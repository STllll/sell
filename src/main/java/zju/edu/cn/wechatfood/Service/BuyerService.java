package zju.edu.cn.wechatfood.Service;

import zju.edu.cn.wechatfood.dto.OrderDTO;

public interface BuyerService {
    //查询一个订单
    OrderDTO findOrderOne(String openid, String orderId);

    //取消订单
    OrderDTO cancelOrder(String openid, String orderId);
}
