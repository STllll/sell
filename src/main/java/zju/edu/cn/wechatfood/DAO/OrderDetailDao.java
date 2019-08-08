package zju.edu.cn.wechatfood.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import zju.edu.cn.wechatfood.dataobjcet.OrderDetail;

import java.awt.print.Pageable;
import java.util.List;

public interface OrderDetailDao extends JpaRepository<OrderDetail, String> {
    List<OrderDetail> findByOrderId(String Openid);


}
