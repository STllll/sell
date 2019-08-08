package zju.edu.cn.wechatfood.Converter;

import org.springframework.beans.BeanUtils;
import zju.edu.cn.wechatfood.dataobjcet.OrderMaster;
import zju.edu.cn.wechatfood.dto.OrderDTO;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMaster2OrderDTOconverter {
    public static OrderDTO convert(OrderMaster ordermaster){
     OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(ordermaster, orderDTO);
        return orderDTO;
    }
    public static List<OrderDTO> covert(List<OrderMaster> orderMasterList){
         return orderMasterList.stream().map(e ->
                 convert(e)
                 ).collect(Collectors.toList());
    }
}
