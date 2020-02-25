package com.atguigu.spring.tx;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;
import java.util.List;

public class SpringTransactionTest {

    private ApplicationContext ctx;
    private BookShopDao bookShopDao;
    private BookShopService bookShopService;
    private Cashier cashier;
    {
        ctx = new ClassPathXmlApplicationContext("beans-properties.xml");
        bookShopDao = (BookShopDao) ctx.getBean("bookShopDao");
        bookShopService = (BookShopService) ctx.getBean("bookShopService");
        cashier = (Cashier) ctx.getBean("cashier");
    }

    @Test
    public void testBookShopDaoFIndPriceByIsbn(){
        System.out.println(bookShopDao.findBookPriceByIsbn("1001"));
    }

    @Test
    public void testBookShopDaoUpdateBookStock(){
        bookShopDao.updateBookStock("1001");
    }

    @Test
    public void testBookShopDaoUpdateUserAccount(){
        bookShopDao.updateUserAccount("AA", 110);
    }

    @Test
    public void testBookShopService(){
        bookShopService.purchase("AA", "1001");
    }

    @Test
    public void testTransactionalPropagation(){
        List<String> isbns = Arrays.asList("1001", "1002");
        cashier.checkout("AA", isbns);
    }
}
