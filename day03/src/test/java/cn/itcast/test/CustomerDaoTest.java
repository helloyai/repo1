package cn.itcast.test;

import cn.itcast.dao.CustomerDao;
import cn.itcast.entity.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class) //声明Spring提供的单元测试环境
@ContextConfiguration(locations = "classpath:applicationContext.xml")   //声明加载Spring配置文件的位置
public class CustomerDaoTest {
    @Autowired
    private CustomerDao customerDao;

    /**
     * 查询所有用户
     */
    @Test
    public void testFindAll() {
        customerDao.findAll().forEach(a-> System.out.println(a));
    }

    /**
     * 查询一个用户
     */
    @Test
    public void testFindOne() {
        Customer one = customerDao.findOne(1L);
        System.out.println(one);
    }

    /**
     * save : 保存或更新
     * 根据传递的对象是否存在主键id,如果没有id主键属性,则保存,
     * 存在id主键属性,则根据id查询数据,更新数据
     */
    @Test
    public void testSave() {
//        Customer customer = new Customer();
//        customer.setCustId(7L);
//        customer.setCustName("meknagrace");
//        customer.setCustSource("Amercian");
//        customerDao.save(customer);
    }

    /**
     * 删除操作
     */
    @Test
    public void testDelete() {
        Customer one = customerDao.findOne(7L);
        if (one != null)
            customerDao.delete(one);
        System.out.println(customerDao.count()+"什么鬼");
    }

    /**
     * 延迟查询
     */
    @Test
    @Transactional
    public void testGetOne() {
        Customer one = customerDao.getOne(1L);
        System.out.println(one.toString());
    }

    /**
     * jpql查询
     */
    @Test
    public void JpqlQuery() {
        List<Customer> lucy = customerDao.findJpql("lucy");
        System.out.println(lucy);
    }

    /**
     * 根据客户名称和客户id查询客户
     */
    @Test
    public void findByCustNameAndCustId() {
        List<Customer> custNameAndAndCustId = customerDao.findByCustNameAndAndCustId("lucy",5L);
        custNameAndAndCustId.forEach(a-> System.out.println(a));
    }

    /**
     * 根据客户名统计个数
     */
    @Test
    public void countByCustName() {
        int count = customerDao.countByCustName("lucy gaga");
        System.out.println(count);
    }

    /**
     * 更新客户
     */
    @Test
    @Transactional
    @Rollback(false)
    public void updateCustomer() {
        customerDao.updateCustomer("elon musk",2L);
        Customer one = customerDao.findOne(2L);
        System.out.println(one);
    }


    /**
     * 本地sql查询
     */
    @Test
    public void findSql() {
        customerDao.findSql().forEach(a-> System.out.println(a));

    }
}
