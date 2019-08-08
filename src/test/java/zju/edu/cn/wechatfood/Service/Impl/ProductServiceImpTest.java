package zju.edu.cn.wechatfood.Service.Impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import zju.edu.cn.wechatfood.dataobjcet.ProductInfo;
import zju.edu.cn.wechatfood.enums.ProductStatusEnum;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceImpTest {
    @Autowired
    private  ProductServiceImp productServiceImp;
//    @Test
//    public void findOne() {
//        Productinfo productinfo = productServiceImp.findOne("123456");
//        Assert.assertEquals("123456",productinfo.getProductId());
//    }
//
//    @Test
//    public void findUpAll() {
//        List<Productinfo> productinfoList = productServiceImp.findUpAll();
//        Assert.assertNotEquals(0,productinfoList.size());
//    }
//
//
//    @Test
//    public void findAll() {
//    PageRequest request = new PageRequest(0, 2);
//       Page<Productinfo> productinfos = productServiceImp.findAll(request);
//        System.out.println(productinfos.getTotalElements());
//    }

//
    @Test
    public void save() {
    ProductInfo productinfo = new ProductInfo();
        productinfo.setProductId("124");
        productinfo.setProductName("双皮奶");
        productinfo.setProductPrice(new BigDecimal(10.5));
        productinfo.setProductStock(200);
        productinfo.setProductDescription("好喝");
        productinfo.setProductIcon("http://xxxxx.jpg");
        productinfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        productinfo.setCategoryType(2);
        ProductInfo result = productServiceImp.save(productinfo);
        Assert.assertNotNull(result);
    }
}