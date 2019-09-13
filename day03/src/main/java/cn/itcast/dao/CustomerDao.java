package cn.itcast.dao;

import cn.itcast.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 符合SpringDataJpa的dao层接口规范
 * JpaRepository<操作的实体类类型,实体类中主键属性的类型></操作的实体类类型,实体类中主键属性的类型>
 * JpaSpecificationExecutor<操作的实体类类型></操作的实体类类型>
 * 封装了复杂的查询操作,分页等操作
 */
@SuppressWarnings("all")
@Repository
public interface CustomerDao extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {
    /**
     * 根据客户名称查询客户
     */
    @Query(value = "from Customer where custName = ?")
     List<Customer> findJpql(String custName);


    /**
     * 根据客户名称和客户id查询客户
     */

     List<Customer> findByCustNameAndAndCustId(String custName,long custId);

    /**
     * 根据客户名称统计个数
     * @param custName 客户名
     * @return
     */
    int countByCustName(String custName);

    /**
     * 更新客户
     */
    @Query(value = "update Customer set custName = ? where custId = ?")
    @Modifying
    void updateCustomer(String custName , long custId );

    /**
     * 本地sql查询
     */

    @Query(value = "select * from cst_customer" , nativeQuery = true)
    List<Customer> findSql();
}
