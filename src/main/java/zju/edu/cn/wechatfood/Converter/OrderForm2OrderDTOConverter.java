package zju.edu.cn.wechatfood.Converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import zju.edu.cn.wechatfood.dataobjcet.OrderDetail;
import zju.edu.cn.wechatfood.dto.OrderDTO;
import zju.edu.cn.wechatfood.enums.ResultEnum;
import zju.edu.cn.wechatfood.exception.SellException;
import zju.edu.cn.wechatfood.form.OrderForm;

import java.util.ArrayList;
import java.util.List;
@Slf4j
public class OrderForm2OrderDTOConverter {
    public static OrderDTO convert(OrderForm orderForm){
        Gson gson = new Gson();
        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        List<OrderDetail> orderDetailList = new ArrayList<>();
        try {
            orderDetailList = gson.fromJson(orderForm.getItems(),
                    new TypeToken<List<OrderDetail>>() {
                    }.getType());
        } catch (Exception e) {
            log.error("【对象转换】错误, string={}", orderForm.getItems());
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }
}
