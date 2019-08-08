package zju.edu.cn.wechatfood.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zju.edu.cn.wechatfood.DAO.SellerInfoDao;
import zju.edu.cn.wechatfood.Service.SellerService;
import zju.edu.cn.wechatfood.dataobjcet.SellerInfo;
@Service
public class SellerServiceImp implements SellerService {

    @Autowired
    private SellerInfoDao sellerInfoDao;


    @Override
    public SellerInfo findSellerInfoByOpenid(String openid) {
        return sellerInfoDao.findByOpenid(openid);
    }
}
