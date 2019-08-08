package zju.edu.cn.wechatfood.DAO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import zju.edu.cn.wechatfood.dataobjcet.OrderMaster;

public interface OrderMasterDao extends JpaRepository<OrderMaster,String> {
    Page<OrderMaster> findByBuyerOpenid(String buyerOpenid, Pageable pageable);

}
