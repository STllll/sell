package zju.edu.cn.wechatfood.form;

import lombok.Data;
import zju.edu.cn.wechatfood.enums.ProductStatusEnum;

import java.math.BigDecimal;
import java.util.Date;
@Data
public class ProductForm {
    private String productId;

    /** 名字. */
    private String productName;

    /** 单价. */
    private BigDecimal productPrice;

    /** 库存. */
    private Integer productStock;

    /** 描述. */
    private String productDescription;

    /** 小图. */
    private String productIcon;


    /** 类目编号. */
    private Integer categoryType;


}
