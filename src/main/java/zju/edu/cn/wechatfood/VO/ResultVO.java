package zju.edu.cn.wechatfood.VO;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ResultVO<T> implements Serializable {

    private static final long serialVersionUID = 543440527377441488L;
    /**错误码**/
    private Integer code;
    /* 具体信息*/
    private String message ;
    /**具体内容**/
    private T data;
}
