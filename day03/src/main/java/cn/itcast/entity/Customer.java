package cn.itcast.entity;


import lombok.Data;
import lombok.ToString;

import javax.persistence.*;


@Data
@ToString(includeFieldNames = true)
@Entity
@Table(name = "cst_customer")
@SuppressWarnings("all")

public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cust_id")
    private Long custId;        //主键映射
    @Column(name = "cust_address")
    private String custAddress; //客户地址
    @Column(name = "cust_industry")
    private String custIndustry;//客户行业
    @Column(name = "cust_level")
    private String custLevel;   //客户级别
    @Column(name = "cust_name")
    private String custName;    //客户姓名
    @Column(name = "cust_phone")
    private String custPhone;   //客户电话
    @Column(name = "cust_source")
    private String custSource;   //客户来源

    @Override
    public String toString() {
        return "Customer{" +
                "custId=" + custId +
                ", custAddress='" + custAddress + '\'' +
                ", custIndustry='" + custIndustry + '\'' +
                ", custLevel='" + custLevel + '\'' +
                ", custName='" + custName + '\'' +
                ", custPhone='" + custPhone + '\'' +
                ", custSource='" + custSource + '\'' +
                '}';
    }
}
