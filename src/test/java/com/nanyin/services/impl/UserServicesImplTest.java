package com.nanyin.services.impl;

import com.nanyin.entity.User;
import com.nanyin.repository.UserRepository;
import com.nanyin.services.UserServices;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * 测试规范：
 * 1.首先对Service进行测试时，应该生成对实现类的Test类，如 userService接口，实现类为userServiceImpl,则应该创建测试类UserServicesImplTest
 * 2.应该对类中的所有的public，protect方法全部进行测试
 * 3.涉及数据库，中间件等使用mock框架模拟mock掉
 * 4.测试方法名称， 原类名Test_测试场景,如：saveUserTest_SuccessWhenCreateNewUser
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServicesImplTest {

    @MockBean
    UserRepository userRepository;

    @Autowired
    UserServices userServices;

    @Test
    public void login() {
    }

    /**
     * 使用Mockito测试service
     * @throws Exception
     */
    @Test
    @Transactional // 测试后自动回滚数据，不会影响数据
    public void saveUser() throws Exception{
        User user = mock(User.class);
        user.setId(1);
        user.setName("zhangsan");
        when(userRepository.findUserByName("zhangsan")).thenReturn(user);
        User result = userServices.findUserByName("zhangsan");

        Assert.assertEquals(user.getId(),result.getId());
    }

    @Test
    public void simpleTest(){

        //创建mock对象，参数可以是类，也可以是接口
        List<String> list = mock(List.class);

        //设置方法的预期返回值
        when(list.get(0)).thenReturn("helloworld");

        String result = list.get(0);

        //验证方法调用(是否调用了get(0))
        Mockito.verify(list).get(0);

        //junit测试
        Assert.assertEquals("helloworld", result);
    }

    @Test
    public void findUsers() {
    }

    @Test
    public void findUserByName() {
    }
}