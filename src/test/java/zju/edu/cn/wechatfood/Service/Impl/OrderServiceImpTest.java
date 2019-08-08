package zju.edu.cn.wechatfood.Service.Impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import zju.edu.cn.wechatfood.dataobjcet.OrderDetail;
import zju.edu.cn.wechatfood.dto.OrderDTO;
import zju.edu.cn.wechatfood.enums.OrderStatusEnum;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImpTest {
  @Autowired
  private OrderServiceImp orderServiceImp;

  private  final String BUYER_OPENID = "150658";

  private final String Order_ID = "1543652094564665709576";
    @Test
    public void createTest() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName("小肥");
        orderDTO.setBuyerAddress("宿舍楼420");
        orderDTO.setBuyerPhone("123456789012");
        orderDTO.setBuyerOpenid(BUYER_OPENID);

        //购物车
        List<OrderDetail> orderDetailList = new ArrayList<>();

        OrderDetail o1 = new OrderDetail();
        o1.setProductId("1");
        o1.setProductQuantity(5);
        orderDetailList.add(o1);
        orderDTO.setOrderDetailList(orderDetailList);

        OrderDTO result = orderServiceImp.create(orderDTO);
        log.info("【创建订单】 result={}",result);
    }

    @Test
    public void findOne() {
        OrderDTO result = orderServiceImp.findOne(Order_ID);
        log.info("[查询单个订单] result={}", result);
        Assert.assertEquals(BUYER_OPENID, result.getBuyerOpenid());
    }

    @Test
    public void findList() {
    }

    @Test
    public void cancel() {
        OrderDTO orderDTO = orderServiceImp.findOne(Order_ID);
        OrderDTO result = orderServiceImp.Cancel(orderDTO);
        Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(), result.getOrderStatus());
    }

    @Test
    public void finish() {
    }

    @Test
    public void paid() {
    }
}