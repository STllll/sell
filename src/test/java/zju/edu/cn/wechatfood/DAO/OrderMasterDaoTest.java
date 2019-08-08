package zju.edu.cn.wechatfood.DAO;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import zju.edu.cn.wechatfood.dataobjcet.OrderMaster;

import java.math.BigDecimal;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterDaoTest {
   @Autowired
   private OrderMasterDao orderMasterDao;
   private  final String OPENID ="110110";
    @Test
    public void saveTest(){
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("123456");
        orderMaster.setBuyerName("小肥");
        orderMaster.setBuyerPhone("133365254356");
        orderMaster.setBuyerAddress("宿舍楼420");
        orderMaster.setBuyerOpenid("110");
        orderMaster.setOrderAmount(new BigDecimal(15));
        OrderMaster  result = orderMasterDao.save(orderMaster);
        Assert.assertNotNull(result);
    }
    @Test
    public void findByBuyerOpenid() {
        //Pageable只是一个接口，不是一个具体的类，所以这里用PageRequest
        PageRequest request = new PageRequest(0,1);
      Page<OrderMaster> result = orderMasterDao.findByBuyerOpenid(OPENID, request);
      System.out.println(result.getTotalElements());

    }
}