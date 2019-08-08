package zju.edu.cn.wechatfood.Service.Impl;


import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.junit4.SpringRunner;
import zju.edu.cn.wechatfood.Service.OrderService;
import zju.edu.cn.wechatfood.Service.PayService;
import zju.edu.cn.wechatfood.dto.OrderDTO;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class PayServiceImpltest {
    @Autowired
    private PayService payService;
    @Autowired
    private OrderService orderService;
    @Test
    public void create(){
    OrderDTO orderDTO =  orderService.findOne("1548137632015559226");
    payService.create(orderDTO);
    }
    @Test
    public void refund() {
        OrderDTO orderDTO = orderService.findOne("1548231018386718548");
        payService.refund(orderDTO);
    }
}
