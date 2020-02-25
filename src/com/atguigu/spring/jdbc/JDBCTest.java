package com.atguigu.spring.jdbc;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCTest {

    private ApplicationContext ctx = null;
    private JdbcTemplate jdbcTemplate;
    private EmployeeDao employeeDao;

    {
        ctx = new ClassPathXmlApplicationContext("beans-properties.xml");
        jdbcTemplate = (JdbcTemplate) ctx.getBean("jdbcTemplate");
        employeeDao = ctx.getBean(EmployeeDao.class);
    }

    @Test
    public void test() throws SQLException {
        DataSource dataSource = ctx.getBean(DataSource.class);
        System.out.println(dataSource.getConnection());
    }

    /**
     * 执行insert, update和delete
     */
    @Test
    public void testUpdate(){
        String sql = "UPDATE employees SET last_name = ? WHERE id = ?";
        jdbcTemplate.update(sql, "Emily", 2);
    }

    /**
     * 执行批量更新
     * 最后一个参数是Object[]的List类型
     */
    @Test
    public void testBatchUpdate(){
        String sql = "INSERT INTO employees (last_name, email, dept_id) VALUES(? ,?, ?)";
        List<Object[]> batchArgs = new ArrayList<>();
        batchArgs.add(new Object[]{"AA", "AA@163.com", 1});
        batchArgs.add(new Object[]{"BB", "BB@163.com", 1});
        batchArgs.add(new Object[]{"CC", "CC@163.com", 1});
        jdbcTemplate.batchUpdate(sql, batchArgs);
    }

    /**
     * 从数据库中获取一条记录，实际得到对应的一个对象
     * 需要调用queryForObject(String sql, RowMapper<T> rowMapper, Object... args)
     * 1. 其中的RowMapper指定如何去映射结果集的行，常用的实现类为BeanPropertyRowMapper
     * 2. 使用sql中列的别名完成列名和类的属性名的映射
     * 3. 不支持级联属性，JdbcTemplate毕竟是一个JDBC的小工具，而不是ORM框架
     */
    @Test
    public void testQueryForObject(){
        String sql = "SELECT id, last_name, email FROM employees WHERE id = ?";
        RowMapper<Employee> rowMapper = new BeanPropertyRowMapper<>(Employee.class);
        Employee employee = jdbcTemplate.queryForObject(sql, rowMapper, 1);
        System.out.println(employee);
    }

    /**
     * 查到实体类的集合
     * 注意调用的不是queryForList 方法
     */
    @Test
    public void testQueryForList(){
        String sql = "SELECT id, last_name, email FROM employees WHERE id > ?";
        RowMapper<Employee> rowMapper = new BeanPropertyRowMapper<>(Employee.class);
        List<Employee> employeeList = jdbcTemplate.query(sql, rowMapper,1);
        System.out.println(employeeList);
    }

    /**
     * 获取单个列的值，或做统计查询
     * 使用queryForObject(String sql, Class<T> requiredType)
     */
    @Test
    public void testQueryForObject2(){
        String sql = "SELECT COUNT(id) FROM employees";
        long count = jdbcTemplate.queryForObject(sql, Long.class);
        System.out.println(count);
    }

    @Test
    public void testEmployeeDao(){
        System.out.println(employeeDao.get(1));
    }
}
