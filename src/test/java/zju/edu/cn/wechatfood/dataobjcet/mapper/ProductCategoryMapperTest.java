package zju.edu.cn.wechatfood.dataobjcet.mapper;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import zju.edu.cn.wechatfood.dataobjcet.ProductCategory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProductCategoryMapperTest {

    @Autowired
    private ProductCategoryMapper mapper;

    @Test
    public void insertByMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("category_name", "肥仔最不爱");
        map.put("category_type", 101);
        int result = mapper.insertByMap(map);
        Assert.assertEquals(1, result);
    }

    @Test
    public void insertByObject() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("小肥最爱");
        productCategory.setCategoryType(102);
        int result = mapper.insertByObject(productCategory);
        Assert.assertEquals(1, result);
    }
    @Test
    public void findByCategoryType() {
            List<ProductCategory> result = mapper.findByCategoryType(102);
            Assert.assertNotEquals(0,result.size());

    }
}