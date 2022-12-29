package com.example;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.domain.Product;
import com.example.domain.User;
import com.example.mapper.ProductMapper;
import com.example.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MybatisPlusPluginsTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ProductMapper productMapper;

    @Test
    public void testPage() {
        //设置分页参数
        //SELECT uid AS id,user_name AS name,age,email,is_delete FROM t_user WHERE is_delete=0 LIMIT ?
        Page<User> page = new Page<>(5, 3);
        userMapper.selectPage(page, null);

        //获取分页数据
        List<User> list = page.getRecords();
        list.forEach(System.out::println);
        System.out.println("当前页：" + page.getCurrent());
        System.out.println("每页显示的条数：" + page.getSize());
        System.out.println("总记录数：" + page.getTotal());
        System.out.println("总页数：" + page.getPages());
        System.out.println("是否有上一页：" + page.hasPrevious());
        System.out.println("是否有下一页：" + page.hasNext());

    }

    @Test
    public void testSelectPageVo() {
        //设置分页参数
        Page<User> page = new Page<>(1, 3);
        userMapper.selectPageVo(page, 20);
        //获取分页数据
        List<User> list = page.getRecords();
        list.forEach(System.out::println);
        System.out.println("当前页：" + page.getCurrent());
        System.out.println("每页显示的条数：" + page.getSize());
        System.out.println("总记录数：" + page.getTotal());
        System.out.println("总页数：" + page.getPages());
        System.out.println("是否有上一页：" + page.hasPrevious());
        System.out.println("是否有下一页：" + page.hasNext());
    }

    @Test
    public void testProduct01(){//模拟修改冲突
        //1查询价格
        Product product1 = productMapper.selectById(1);
        System.out.println("product1查询的价格为：" + product1.getPrice());
        //2查询价格
        Product product2 = productMapper.selectById(1);
        System.out.println("product2查询的价格为：" + product2.getPrice());
        //1的价格加50
        product1.setPrice(product1.getPrice()+50);
        productMapper.updateById(product1);
        //2的价格-50
        product2.setPrice(product2.getPrice()-30);
        int result = productMapper.updateById(product2);
        if (result == 0){
            //操作失败，重试
           Product productNew = productMapper.selectById(1);
           productNew.setPrice(productNew.getPrice()-30);
           productMapper.updateById(productNew);
        }
        //老板查询的价格
        //最后的结果
        Product p3 = productMapper.selectById(1L);
        //价格覆盖，最后的结果：70
        System.out.println("最后的结果：" + p3.getPrice());
    }

}
