package zju.edu.cn.wechatfood.dataobjcet.dao;

import org.springframework.beans.factory.annotation.Autowired;
import zju.edu.cn.wechatfood.dataobjcet.mapper.ProductCategoryMapper;

import java.util.Map;

public class ProductCategoryDao {

    @Autowired
    ProductCategoryMapper mapper;

    public int insertByMap(Map<String, Object> map){
        return mapper.insertByMap(map);

    }
}

