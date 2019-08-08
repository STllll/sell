package zju.edu.cn.wechatfood.Service;

import zju.edu.cn.wechatfood.dataobjcet.ProductCategory;

import java.util.List;

public interface Category {
    ProductCategory findOne(Integer categoryId);

    List<ProductCategory> findAll();

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    ProductCategory save(ProductCategory productCategory);
}
