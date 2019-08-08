package zju.edu.cn.wechatfood.Service;

import zju.edu.cn.wechatfood.dto.OrderDTO;

/*
* 消息推送
* */
public interface PushMessageService {
    /*
    * 订单状态
    * */
    void orderStatus(OrderDTO orderDTO) ;


}
