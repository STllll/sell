package zju.edu.cn.wechatfood.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zju.edu.cn.wechatfood.dataobjcet.ProductInfo;
import zju.edu.cn.wechatfood.dto.CartDTO;

import java.util.List;

public interface ProductService {

    ProductInfo findOne(String productId);

    /**
     * 查询所有在架商品列表
     * @return
     */
    List<ProductInfo> findUpAll();

    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);

    //加库存
    void increaseStock(List<CartDTO> cartDTOList);

    //减库存
    void decreaseStock(List<CartDTO> cartDtoList);

    //上架
    ProductInfo onSale(String productId);

    //下架
    ProductInfo offSale(String productId);
}
