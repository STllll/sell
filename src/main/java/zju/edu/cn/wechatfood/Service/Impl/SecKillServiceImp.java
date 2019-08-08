package zju.edu.cn.wechatfood.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import zju.edu.cn.wechatfood.Service.RedisLock;
import zju.edu.cn.wechatfood.Service.SecKillService;
import zju.edu.cn.wechatfood.exception.SellException;
import zju.edu.cn.wechatfood.utils.KeyUtil;

import java.util.HashMap;
import java.util.Map;

@Service
public class SecKillServiceImp implements SecKillService {
    static final int TIMEOUT = 10* 1000; //超时时间10秒
    @Autowired
    private RedisLock redisLock;

    static Map<String, Integer> products;
    static Map<String, Integer> stock;
    static Map<String, String> orders;
    static
    {
        products = new HashMap<>();
        stock = new HashMap<>();
        orders = new HashMap<>();
        products.put("123456",100000);
        stock.put("123456",100000);
    }
    private String queryMap(String productId) {
       return  "国庆活动,咸鸭蛋特价,限量" + products.get(productId) +"份" +
               "还剩:" + stock.get(productId) + "份"
               +"该商品成功下单用户数目: " +
               orders.size() + " 人";

    }
    @Override
    public String querySecKillProducInfo(String productId) {
        return this.queryMap(productId);
    }

    @Override
    public void orderProductMockDiffUser(String productId) {

         //加锁
          long time = System.currentTimeMillis() + TIMEOUT;
         if(!redisLock.lock(productId, String.valueOf(time))) {
             throw new SellException(101, "哎呦喂,人也太多了,换个姿势再试试~");
        }

         //1.查询该商品库存,为0则活动结束
          int stockNum = stock.get(productId);
          if(stockNum == 0) {
              throw new SellException(100, "活动结束");
          } else {
              orders.put(KeyUtil.genUniqueKey(), productId);
              stockNum = stockNum - 1;

          try {
              Thread.sleep(100);
          } catch (InterruptedException e) {
              e.printStackTrace();
          }
          stock.put(productId, stockNum);
          }
          //解锁
         redisLock.unlock(productId, String.valueOf(time));
    }
}
