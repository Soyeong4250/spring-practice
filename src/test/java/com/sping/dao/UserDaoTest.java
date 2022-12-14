package com.sping.dao;

import com.sping.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserDaoFactory.class)
class UserDaoTest {
    @Autowired
    ApplicationContext context;
    UserDao userDao;

    @BeforeEach
    void setUp() {
        this.userDao = context.getBean("awsUserDao", UserDao.class);
        userDao.deleteAll();
    }

    @Test
    @DisplayName("user가 null인 경우")
    void findById() {
        assertThrows(EmptyResultDataAccessException.class, () -> {
            userDao.selectById("1");
        });
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

    @Test
    @DisplayName("selectAll 테스트")
    void selectAll() {
        List<User> userList = userDao.selectAll();
        assertEquals(0, userList.size());
        User user1 = new User("1", "Kyeonghwan", "1123");
        userDao.add(user1);
        User user2 = new User("2", "Sujin", "4321");
        userDao.add(user2);
        userList = userDao.selectAll();
        assertEquals(2, userList.size());
    }

}