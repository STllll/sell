package zju.edu.cn.wechatfood.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import zju.edu.cn.wechatfood.dataobjcet.ProductInfo;

import java.util.List;

public interface ProductInfoDao extends JpaRepository<ProductInfo, String> {
   List<ProductInfo> findByProductStatus(Integer productStatus);
//   List<ProductInfo> findByProductId(String productId);
}
