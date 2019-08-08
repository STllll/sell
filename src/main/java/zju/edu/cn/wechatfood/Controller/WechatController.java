package zju.edu.cn.wechatfood.Controller;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.apache.catalina.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import zju.edu.cn.wechatfood.config.ProjectUrlConfig;
import zju.edu.cn.wechatfood.enums.ResultEnum;
import zju.edu.cn.wechatfood.exception.SellException;

import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

@RequestMapping("/wechat")
@Controller
@Slf4j
public class WechatController {
    @Autowired
    private WxMpService wxOpenService;
    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private ProjectUrlConfig projectUrlConfig;
    @GetMapping("/authorize")
     public String authorize(@RequestParam("returnUrl") String returnUrl){
        //1.配置
        //2.调用方法
        String url = projectUrlConfig.getWechatMpAuthorize()+"/sell/wechat/userInfo";
        String redirectUrl = wxMpService.oauth2buildAuthorizationUrl(url,WxConsts.OAUTH2_SCOPE_USER_INFO, URLEncoder.encode(returnUrl));
     log.info("[微信网页授权] 获取code, result={}",redirectUrl);

     return "redirect:" + redirectUrl;
     }

     @GetMapping("/userInfo")
     public String userInfo(@RequestParam("code") String code,
                          @RequestParam("state") String returnUrl) {
     WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
     try{
        wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
     } catch(WxErrorException e) {
         log.error("[微信网页授权] {}", e);
         throw new SellException(ResultEnum.WECHAT_MP_ERROR.getCode(), e.getError().getErrorMsg());
     }
     String openId = wxMpOAuth2AccessToken.getOpenId();
     return "redirect:" + returnUrl + "?openid=" + openId;

     }

     @GetMapping("/qrAuthorize")
    public String qrAuthorize(@RequestParam("code") String code,
                              @RequestParam("state") String returnUrl) {
        String url = projectUrlConfig.getWechatOpenAuthorize() + "/sell/wechat/qrUserInfo";
        String redirectUrl = wxOpenService.buildQrConnectUrl(url, WxConsts.QRCONNECT_SCOPE_SNSAPI_LOGIN, URLEncoder.encode(returnUrl));
         return "redirect:" + redirectUrl;
     }

     @GetMapping("/qrUserInfo")
    public String qrUserInfo(@RequestParam("code") String code,
                             @RequestParam("state") String returnUrl) {
         WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
         try{
             wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
         } catch(WxErrorException e) {
             log.error("[微信网页授权] {}", e);
             throw new SellException(ResultEnum.WECHAT_MP_ERROR.getCode(), e.getError().getErrorMsg());
         }
         String openId = wxMpOAuth2AccessToken.getOpenId();
         return "redirect:" + returnUrl + "?openid=" + openId;
     }



}
