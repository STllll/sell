package zju.edu.cn.wechatfood.Service.Impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import zju.edu.cn.wechatfood.Service.OrderService;
import zju.edu.cn.wechatfood.Service.PushMessageService;
import zju.edu.cn.wechatfood.dto.OrderDTO;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PushMessageServiceImpTest {

    @Autowired
    private PushMessageService pushMessageService;

    @Autowired
    private OrderService orderService;
    @Test
    public void orderStatus() {

    OrderDTO orderDto = orderService.findOne("123");
    pushMessageService.orderStatus(orderDto);
    }
}