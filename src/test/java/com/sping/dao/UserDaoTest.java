package com.sping.dao;

import com.sping.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserDaoFactory.class)
class UserDaoTest {
    @Autowired
    ApplicationContext context;
    UserDao userDao;

    @BeforeEach
    void setUp() {
        userDao = context.getBean("awsUserDao", UserDao.class);
        userDao.deleteAll();
    }

    @Test
    @DisplayName("add기능 & select 테스트")
    void addAndSelectById() {
        User user = new User("0", "Soyeong", "1234");
        userDao.add(user);

        assertEquals("Soyeong", userDao.selectById("0").getName());
    }


    @Test
    @DisplayName("데이터 개수 세기 테스트")
    void getCount() {
        assertEquals(0, userDao.getCount());
        User user = new User("0", "Soyeong", "1234");
        userDao.add(user);

        assertEquals(1, userDao.getCount());
    }

    @Test
    @DisplayName("deletAll 테스트")
    void deleteAll() {
        userDao.deleteAll();
        assertEquals(0, userDao.getCount());
    }

}