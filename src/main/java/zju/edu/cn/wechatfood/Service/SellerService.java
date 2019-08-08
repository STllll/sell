package zju.edu.cn.wechatfood.Service;

import zju.edu.cn.wechatfood.dataobjcet.SellerInfo;

public interface SellerService {
    SellerInfo findSellerInfoByOpenid(String openid);
}
