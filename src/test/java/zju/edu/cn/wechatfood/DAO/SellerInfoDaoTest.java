package zju.edu.cn.wechatfood.DAO;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import zju.edu.cn.wechatfood.dataobjcet.SellerInfo;
import zju.edu.cn.wechatfood.utils.KeyUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerInfoDaoTest {
    @Autowired
    private SellerInfoDao sellerInfoDao;

    @Test
    public void save() {
        SellerInfo sellerinfo = new SellerInfo();
        sellerinfo.setSellerId(KeyUtil.genUniqueKey());
        sellerinfo.setUsername("admin");
        sellerinfo.setPassword("admin");
        sellerinfo.setOpenid("abc");

        SellerInfo result = sellerInfoDao.save(sellerinfo);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByOpenid(){
        SellerInfo result = sellerInfoDao.findByOpenid("abc");
        Assert.assertEquals("abc", result.getOpenid());
    }
}