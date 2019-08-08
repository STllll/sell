package zju.edu.cn.wechatfood.Service.Impl;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import zju.edu.cn.wechatfood.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zju.edu.cn.wechatfood.DAO.ProductInfoDao;
import zju.edu.cn.wechatfood.dataobjcet.ProductInfo;
import zju.edu.cn.wechatfood.dto.CartDTO;
import zju.edu.cn.wechatfood.enums.ProductStatusEnum;
import zju.edu.cn.wechatfood.enums.ResultEnum;
import zju.edu.cn.wechatfood.exception.SellException;

import java.util.List;
@Service
@Transactional
//@CacheConfig(cacheNames = "xxx")
public class ProductServiceImp implements ProductService {

    @Autowired
    private ProductInfoDao repository;

    @Override
//    @Cacheable(cacheNames = "product", key = "123")
    public ProductInfo findOne(String productId) {
        return repository.findOne(productId);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return repository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
//    @CachePut(cacheNames = "product", key = "123")
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }

    @Override
    public void increaseStock(List<CartDTO> cartDTOList) {
    for (CartDTO cartDto: cartDTOList) {
        ProductInfo productInfo = repository.findOne(cartDto.getProductId());
        if (productInfo == null){
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIT);
        }
        Integer result = productInfo.getProductStock() + cartDto.getProductQuantity();
        productInfo.setProductStock(result);
        repository.save(productInfo);
    }

    }

    @Override
    public void decreaseStock(List<CartDTO> cartDtoList) {
       for (CartDTO cartDTO : cartDtoList){
           ProductInfo productInfo = repository.findOne(cartDTO.getProductId());
           if (productInfo == null) {
               throw new SellException(ResultEnum.PRODUCT_NOT_EXIT);
           }
           Integer result = productInfo.getProductStock() - cartDTO.getProductQuantity();
           if( result < 0){
               throw new SellException((ResultEnum.PRODUCT_NOT_EXIT));
           }
           productInfo.setProductStock(result);
           repository.save(productInfo);
       }
    }

    @Override
    public ProductInfo onSale(String productId) {
        ProductInfo productInfo = repository.findOne(productId);
        if (productInfo == null) {
              throw new SellException(ResultEnum.PRODUCT_NOT_EXIT);
        }
        if (productInfo.getProductStatusEnum() == ProductStatusEnum.UP){
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        //更新
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        return repository.save(productInfo);
    }

    @Override
    public ProductInfo offSale(String productId) {
        ProductInfo productInfo = repository.findOne(productId);
        if (productInfo == null) {
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIT);
        }
        if (productInfo.getProductStatusEnum() == ProductStatusEnum.DOWN){
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        //更新
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        return repository.save(productInfo);
    }
}
