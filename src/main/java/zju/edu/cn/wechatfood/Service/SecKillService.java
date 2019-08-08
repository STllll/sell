package zju.edu.cn.wechatfood.Service;

public interface SecKillService {
    String querySecKillProducInfo(String productId);

    void  orderProductMockDiffUser(String productId);
}
