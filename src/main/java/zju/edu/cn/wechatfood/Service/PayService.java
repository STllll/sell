package zju.edu.cn.wechatfood.Service;

import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import zju.edu.cn.wechatfood.dto.OrderDTO;

public interface PayService {
    PayResponse create(OrderDTO orderDto);
    PayResponse notify(String notifyData);

    RefundResponse refund(OrderDTO orderDTO);
}
