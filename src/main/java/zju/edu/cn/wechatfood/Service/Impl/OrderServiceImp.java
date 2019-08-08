package zju.edu.cn.wechatfood.Service.Impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import zju.edu.cn.wechatfood.Converter.OrderMaster2OrderDTOconverter;
import zju.edu.cn.wechatfood.DAO.OrderDetailDao;
import zju.edu.cn.wechatfood.DAO.OrderMasterDao;
import zju.edu.cn.wechatfood.Service.*;
import zju.edu.cn.wechatfood.dataobjcet.OrderDetail;
import zju.edu.cn.wechatfood.dataobjcet.OrderMaster;
import zju.edu.cn.wechatfood.dataobjcet.ProductInfo;
import zju.edu.cn.wechatfood.dto.CartDTO;
import zju.edu.cn.wechatfood.dto.OrderDTO;
import zju.edu.cn.wechatfood.enums.OrderStatusEnum;
import zju.edu.cn.wechatfood.enums.PayStatusEnum;
import zju.edu.cn.wechatfood.enums.ProductStatusEnum;
import zju.edu.cn.wechatfood.enums.ResultEnum;
import zju.edu.cn.wechatfood.exception.ResponseBankException;
import zju.edu.cn.wechatfood.exception.SellException;
import zju.edu.cn.wechatfood.utils.KeyUtil;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class OrderServiceImp  implements OrderService {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderDetailDao orderDetailDao;

    @Autowired
    private OrderMasterDao orderMasterDao;

    @Autowired
    private PushMessageService pushMessageService;

    @Autowired
    private WebSocket webSocket;

    @Autowired
    private PayService payService;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        String orderId = KeyUtil.genUniqueKey();
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
        //1. 查询商品（数量,价格）
        for(OrderDetail orderDetail : orderDTO.getOrderDetailList()){
            ProductInfo productInfo = productService.findOne(orderDetail.getProductId());
            if(productInfo == null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIT);
//                throw new ResponseBankException();
            }
        //2. 计算订单总价
           orderAmount = productInfo.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                     .add(orderAmount);
        // 订单详情入库
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetail.setOrderId(orderId);
            BeanUtils.copyProperties(productInfo, orderDetail);
        orderDetailDao.save(orderDetail);
        }
        //3. 写入订单数据库（orderMaster和orderDetail）
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterDao.save(orderMaster);
        //4. 扣库存
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(e ->
                new CartDTO(e.getProductId(), e.getProductQuantity())
                ).collect(Collectors.toList());
        productService.decreaseStock(cartDTOList);
        //发送webSocket消息
        webSocket.sendMessage(orderDTO.getOrderId());
        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        OrderMaster orderMaster = orderMasterDao.findOne(orderId);
        if(orderMaster == null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        List<OrderDetail> orderDetailList = orderDetailDao.findByOrderId(orderId);
         if(CollectionUtils.isEmpty(orderDetailList)){
             throw new SellException(ResultEnum.ORDER_NOT_EXIST);
         }
       OrderDTO orderDTO = new OrderDTO();
         BeanUtils.copyProperties(orderMaster, orderDTO);
         orderDTO.setOrderDetailList(orderDetailList);
         return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterDao.findByBuyerOpenid(buyerOpenid, pageable);
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOconverter.covert(orderMasterPage.getContent());
        return new PageImpl<OrderDTO>(orderDTOList,pageable, orderMasterPage.getTotalElements() );

    }

    @Override
    public OrderDTO Cancel(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();

        //判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("[取消订单] 订单状态不正确, orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster updateOrderMaster =orderMasterDao.save(orderMaster);
        if(updateOrderMaster == null){
            log.error("[取消订单] 更新失败, orderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        //返回库存
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("【取消订单】订单中无商品详情, orderDTO={}", orderDTO);
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
                .map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        productService.increaseStock(cartDTOList);
        //如已支付，退款
        if (orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS)){
             payService.refund(orderDTO);
        }
        return orderDTO;
    }

    @Override
//    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {
        //判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【完结订单】订单状态不正确, orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster updateResult = orderMasterDao.save(orderMaster);
        if (updateResult == null) {
            log.error("【完结订单】更新失败, orderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        //推送微信模版消息
        pushMessageService.orderStatus(orderDTO);

        return orderDTO;
    }

    @Override
    public OrderDTO paid(OrderDTO orderDTO) {
        //判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【订单支付完成】订单状态不正确, orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //判断支付状态
        if (!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())) {
            log.error("【订单支付完成】订单支付状态不正确, orderDTO={}", orderDTO);
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }

        //修改支付状态
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster updateResult = orderMasterDao.save(orderMaster);
        if (updateResult == null) {
            log.error("【订单支付完成】更新失败, orderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterDao.findAll(pageable);
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOconverter.covert(orderMasterPage.getContent());
        return new PageImpl<OrderDTO>(orderDTOList,pageable, orderMasterPage.getTotalElements() );

    }
}
