package com.example;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.domain.User;
import com.example.mapper.UserMapper;
import org.junit.Test;
import org.junit.platform.commons.util.StringUtils;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MybatisPlusWrapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelectList() {
        //查询用户名包含yyb，年龄20-24，邮箱不为null的信息
        //SELECT uid AS id,user_name AS name,age,email,is_delete FROM t_user
        // WHERE is_delete=0 AND (user_name LIKE ? AND age BETWEEN ? AND ? AND email IS NOT NULL
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        queryWrapper.like("user_name", "yyb").between("age", 20, 24).isNotNull("email");
        List<User> list = userMapper.selectList(queryWrapper);
        list.forEach(System.out::println);
    }


    @Test
    public void testSelectList02() {
        //按年龄降序查询用户，如果年龄相同则按id升序排列
        //SELECT uid AS id,user_name AS name,age,email,is_delete FROM t_user WHERE is_delete=0 ORDER BY age DESC,uid ASC
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("age").orderByAsc("uid");
        List<User> list = userMapper.selectList(queryWrapper);
        list.forEach(System.out::println);
    }

    @Test
    public void test03() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNull("email");
        int result = userMapper.delete(queryWrapper);
        System.out.println(result);
    }

    @Test
    public void test04() {
        //UPDATE t_user SET user_name=?, email=? WHERE is_delete=0 AND (age > ? AND user_name LIKE ? OR email IS NOT NULL)
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.gt("age", 20).like("user_name", "a").or().isNotNull("email");
        User user = new User();
        user.setName("xiaoming");
        user.setEmail("test@qq.com");
        int result = userMapper.update(user, queryWrapper);
        System.out.println("result:" + result);
    }

    @Test
    public void test05() {
        // SELECT user_name,age FROM t_user WHERE is_delete=0
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("user_name", "age");

        List<Map<String, Object>> maps = userMapper.selectMaps(queryWrapper);
        maps.forEach(System.out::println);

    }

    @Test
    public void test06() {
        //子查询
        //SELECT uid AS id,user_name AS name,age,email,is_delete FROM t_user WHERE is_delete=0 AND (uid IN (select uid from t_user where uid >= 100))
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.inSql("uid", "select uid from t_user where uid >= 100");
        List<User> list = userMapper.selectList(queryWrapper);
        list.forEach(System.out::println);
    }

    @Test
    public void test07() {
        //UPDATE t_user SET user_name=?,email=? WHERE is_delete=0 AND (user_name LIKE ? AND (age > ? OR email IS NOT NULL))
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.like("user_name", "a")
                .and(i -> i.gt("age", 20).or().isNotNull("email"));
        updateWrapper.set("user_name", "xiaohei").set("email", "abc@qq.com");
        int result = userMapper.update(null, updateWrapper);
        System.out.println(result);
    }

    @Test
    public void test08() {
        //SELECT uid AS id,user_name AS name,age,email,is_delete FROM t_user WHERE is_delete=0 AND (user_name LIKE ? AND age >= ? AND age <= ?)
        String username = "a";
        Integer ageBegin = 20;
        Integer ageEnd = 26;
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(username)) {
            //isNotBlank 判断某个字符串是否不为空字符串，不为null，不为空白符
            queryWrapper.like("user_name", username);
        }
        if (ageBegin != null) {
            queryWrapper.ge("age", ageBegin);
        }
        if (ageEnd != null) {
            queryWrapper.le("age", ageEnd);
        }
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    @Test
    public void test09() {
        // SELECT uid AS id,user_name AS name,age,email,is_delete FROM t_user WHERE is_delete=0 AND (user_name LIKE ? AND age <= ?)
        String username = "a";
        Integer ageBegin = null;
        Integer ageEnd = 24;
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(com.baomidou.mybatisplus.core.toolkit.StringUtils.isNotBlank(username), "user_name", username)
                .ge(ageBegin != null, "age", ageBegin)
                .le(ageEnd != null, "age", ageEnd);

        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }
}
