package zju.edu.cn.wechatfood.dto;

import lombok.Data;
import zju.edu.cn.wechatfood.dataobjcet.ProductInfo;

@Data
public class CartDTO {
    private String productId;
    private Integer productQuantity;

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
