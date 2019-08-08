package zju.edu.cn.wechatfood.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zju.edu.cn.wechatfood.DAO.ProductCategoryDao;
import zju.edu.cn.wechatfood.Service.Category;
import zju.edu.cn.wechatfood.dataobjcet.ProductCategory;

import javax.transaction.Transactional;
import java.util.List;
@Service
@Transactional
public class CategoryServiceImp  implements Category{
    @Autowired
    private ProductCategoryDao repository;


    @Override
    public ProductCategory findOne(Integer categoryId) {
        return repository.findOne(categoryId);
    }

    @Override
    public List<ProductCategory> findAll() {
        return repository.findAll();
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        return repository.findByCategoryTypeIn(categoryTypeList);
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return repository.save(productCategory);
    }
}
