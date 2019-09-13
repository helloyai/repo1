package cn.itcast.test;

import cn.itcast.dao.CustomerDao;
import cn.itcast.entity.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.criteria.*;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class) //声明spring单元测试环境
@ContextConfiguration(locations = "classpath:applicationContext.xml") //声明加载springContext.xml配置文件的位置
public class CustomerDaoHighLevelTest {
    @Autowired
    private CustomerDao customerDao;

    /**
     * 根据指定条件创建高级查询
     * 根据custName来查询
     */
    @Test
    public void testFindAll() {
        Specification <Customer> specification = new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                //获取比较的属性
                Path<Object> custName = root.get("custName");

                //构建查询条件
                return criteriaBuilder.equal(custName, "Lucy");
            }
        };
        List<Customer> all = customerDao.findAll(specification);
        all.forEach(a-> System.out.println(a));
    }


    /**
     * 多个条件组合查询
     * 根据指定条件创建高级查询
     * 根据custName和id来查询
     */
    @Test
    public void testSpecFind() {
        Specification<Customer> spec = new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                //获取比较的属性
                Path<Object> custId = root.get("custId");
                Path<Object> custName = root.get("custName");
                //设置比较条件(构建查询条件)
                Predicate s1 = criteriaBuilder.equal(custId, 1L);
                Predicate lucy = criteriaBuilder.equal(custName, "lucy");

                //将多个查询条件组合在一起
                Predicate and = criteriaBuilder.and(s1, lucy);
                return and;

            }
        };

        List<Customer> list = customerDao.findAll(spec);
        list.forEach(a-> System.out.println(a));
    }


    /**
     * 模糊查询条件
     */
    @Test
    public void testLikeQuery() {
        Specification<Customer> spec = new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path<Object> custName = root.get("custName");
                return criteriaBuilder.like(custName.as(String.class), "%l%");
            }
        };
        List<Customer> all = customerDao.findAll(spec);
        for (Customer customer : all) {
            System.out.println(customer);
        }
    }


    /**
     * 多个模糊查询条件
     */
    @Test
    public void testMuliLikeQuery() {
        Specification<Customer> spec = new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path<Object> custName = root.get("custName"); //查询属性:客户名
                Path<Object> custIndustry = root.get("custIndustry");//查询属性:客户行业
                Path<Object> custId = root.get("custPhone");//查询属性:客户id

                Predicate name = criteriaBuilder.like(custName.as(String.class), "%l%");
                Predicate industry = criteriaBuilder.like(custIndustry.as(String.class), "h%");
                Predicate id = criteriaBuilder.like(custId.as(String.class), "%8%");
                Predicate nameAndIndustry = criteriaBuilder.and(name, industry);
                return criteriaBuilder.or(nameAndIndustry, id);
            }
        };
        List<Customer> all = customerDao.findAll(spec);
        for (Customer customer : all) {
            System.out.println(customer);
        }
    }


    /**
     * 按指定方式排序查询
     */
    @Test
    public void testSortFindAll() {
        //指定查询条件为null
        Specification<Customer> specification = null;

        //设置排序方式
        Sort sort = new Sort(Sort.Direction.DESC,"custId");
        List<Customer> all = customerDao.findAll(specification, sort);
        for (Customer customer : all) {
            System.out.println(customer);
        }
    }

    /**
     * 分页查询
     * 分页排序查询
     */
    @Test
    public void testSplitPageFindAll() {
        Specification<Customer> specification = null;

        //分页排序查询
        //指定排序方式
        Sort sort = new Sort(Sort.Direction.DESC, "custId");
        //设置分页条件
        Pageable pageable = new PageRequest(0, 2, sort);

        //开启查询
        Page<Customer> all = customerDao.findAll(specification, pageable);
        List<Customer> content = all.getContent();
        System.out.println(content.toString());
        for (Customer customer : all) {
            System.out.println(customer);
        }


/*分页查询
        Pageable pageable = new PageRequest(0, 2);

        Page<Customer> all = customerDao.findAll(specification, pageable);
        for (Customer customer : all) {
            System.out.println(customer);
        }
*/
    }

}
