package zju.edu.cn.wechatfood.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
public class ProductVO  implements Serializable {

    private static final long serialVersionUID = -1622965945236646716L;
    @JsonProperty("name")
    private String categoryName;
    @JsonProperty("type")
    private Integer categoryType;
    @JsonProperty("foods")
    private List<ProductInfoVO> productInfoVOList;

}
