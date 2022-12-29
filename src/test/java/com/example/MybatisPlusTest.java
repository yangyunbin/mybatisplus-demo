package com.example;


import com.example.domain.User;
import com.example.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MybatisPlusTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelectList() {
        List<User> list = userMapper.selectList(null);
        list.forEach(System.out::println);
    }


    @Test
    public void testInsert() {
        //新增用户
        //INSERT INTO user ( id, name, age, email ) VALUES ( ?, ?, ?, ? )
        User user = new User();
        //user.setId(100L);
        user.setName("zhangsan");
        user.setAge(18);
        user.setEmail("zhangsan@qq.com");
        int result = userMapper.insert(user);
        System.out.println("result:" + result);
        System.out.println("id:" + user.getId());
    }

    @Test
    public void testDelete() {
        //DELETE FROM user WHERE id=?  通过id删除
/*        int result = userMapper.deleteById(1608013835317399553L);
        System.out.println("result:" + result);*/

        // DELETE FROM user WHERE name = ? AND age = ?   根据map设置的条件删除
/*        Map<String,Object> map = new HashMap<>();
        map.put("name","zhangsan");
        map.put("age",23);
        int result = userMapper.deleteByMap(map);
        System.out.println("result:" + result);*/

        //通过多个id删除，DELETE FROM user WHERE id IN ( ? , ? , ? )
        List<Long> idList = Arrays.asList(1L, 2L, 3L);
        int result = userMapper.deleteBatchIds(idList);
        System.out.println("result:" + result);
    }

    @Test
    public void testUpdateById() {
        //根据id修改，UPDATE user SET name=?, email=? WHERE id=?
        User user = new User();
        user.setId(4L);
        user.setName("lisi");
        user.setEmail("lisi@qq.com");
        int result = userMapper.updateById(user);
        System.out.println("result:" + result);
    }

    @Test
    public void testSelectById() {
        // Preparing: SELECT id,name,age,email FROM user WHERE id=?
        User user = userMapper.selectById(1L);
        System.out.println(user);
    }


    @Test
    public void testSelectBatchIds() {
        //根据多个id查询多个用户信息
        //SELECT id,name,age,email FROM user WHERE id IN ( ? , ? )
        List<Long> idList = Arrays.asList(4L, 5L);
        List<User> userList = userMapper.selectBatchIds(idList);
        userList.forEach(System.out::println);

    }

    @Test
    public void testSelectByMap() {
        //SELECT id,name,age,email FROM user WHERE name = ? AND age = ?
        HashMap<String, Object> map = new HashMap<>();
        map.put("age", 28);
        map.put("name", "Tom");
        List<User> userList = userMapper.selectByMap(map);
        userList.forEach(System.out::println);
    }

    @Test
    public void testSelectAll() {
        //SELECT id,name,age,email FROM user
        //逻辑删除，SELECT uid AS id,user_name AS name,age,email,is_delete FROM t_user WHERE is_delete=0
        List<User> list = userMapper.selectList(null);
        list.forEach(System.out::println);
    }

    @Test
    public void testSelectMapById() {
        Map<String, Object> map = userMapper.selectMapById(1L);
        System.out.println(map);
    }


}
