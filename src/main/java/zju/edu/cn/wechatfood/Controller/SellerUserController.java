package zju.edu.cn.wechatfood.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import zju.edu.cn.wechatfood.Service.SellerService;
import zju.edu.cn.wechatfood.config.ProjectUrlConfig;
import zju.edu.cn.wechatfood.constant.CookieConstant;
import zju.edu.cn.wechatfood.constant.RedisConstant;
import zju.edu.cn.wechatfood.dataobjcet.SellerInfo;
import zju.edu.cn.wechatfood.enums.ResultEnum;
import zju.edu.cn.wechatfood.exception.SellException;
import zju.edu.cn.wechatfood.utils.CookieUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

//卖家用户
@Controller
@RequestMapping("/seller")
public class SellerUserController {
    @Autowired
    private SellerService  sellerService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ProjectUrlConfig projectUrlConfig;
    @GetMapping("/login")
    public ModelAndView login(@RequestParam("openid") String openid,
                              HttpServletResponse response,
                              Map<String, Object> map) {
        //1.openid去和数据库的数据匹配
        SellerInfo sellerInfo = sellerService.findSellerInfoByOpenid(openid);
        if (sellerInfo == null) {
             map.put("msg", ResultEnum.LOGIN_FAIL.getMessage());
             map.put("url", "/sell/seller/order/list");
             return new ModelAndView("common/error");
        }
        //2.设置token至redis
        String token = UUID.randomUUID().toString();
        Integer expire = RedisConstant.EXPIRE;
        redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX, token), openid, expire, TimeUnit.SECONDS);
        //3.设置token至cookie
        CookieUtil.set(response, CookieConstant.TOKEN, token, expire);
        return new ModelAndView("redirect" + projectUrlConfig.getSell() +  "/sell/seller/order/list");
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Map<String, Object> map) {

     // 1.从cookie里查询
     Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);


     //2. 清除redis
        if (cookie != null) {
            redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));
     //3. 清除cookie
            CookieUtil.set(response, CookieConstant.TOKEN, null, 0);
     }
     map.put("msg", ResultEnum.LOGOUT_SUCCESS);
        map.put("ulr", "/sell/seller/order/list");
        return new ModelAndView("common/success", map);
    }
}
