package zju.edu.cn.wechatfood.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import zju.edu.cn.wechatfood.dataobjcet.ProductCategory;

import java.util.List;
//public interface ProductCategoryDao  extends JpaRepository<ProductCategory, Integer> {
//    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
//
//}
public interface ProductCategoryDao extends JpaRepository<ProductCategory, Integer> {

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}
