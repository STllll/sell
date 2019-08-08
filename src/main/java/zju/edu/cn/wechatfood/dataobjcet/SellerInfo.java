package zju.edu.cn.wechatfood.dataobjcet;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class SellerInfo {

    @Id
    private String sellerId;

    private String username;

    private String password;

    private String openid;
}
