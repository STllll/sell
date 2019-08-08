package zju.edu.cn.wechatfood.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import zju.edu.cn.wechatfood.dataobjcet.SellerInfo;

public interface SellerInfoDao extends JpaRepository<SellerInfo, String> {
   SellerInfo findByOpenid(String openid);
}
