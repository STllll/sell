package zju.edu.cn.wechatfood.Service.Impl;


import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import zju.edu.cn.wechatfood.Service.SellerService;
import zju.edu.cn.wechatfood.dataobjcet.SellerInfo;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SellerServiceImpTest {
    private static final  String openid = "abc";

    @Autowired
    private SellerServiceImp sellerServiceImp;

    @Test
    public void  findSellerInfoByOpenid() {
        SellerInfo result = sellerServiceImp.findSellerInfoByOpenid(openid);
        Assert.assertEquals(openid, result.getOpenid());
    }

}
