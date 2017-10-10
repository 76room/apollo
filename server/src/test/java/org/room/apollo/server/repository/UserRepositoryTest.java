package org.room.apollo.server.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.room.apollo.server.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by Alexey on 10/7/17.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository mongoRepository;

    private String TEST_EMAIL_1 = "1@mail.ru";
    private String TEST_EMAIL_2 = "2@mail.ru";
    private String PASSWORD = "password";

    @Before
    public void setUp() throws Exception {
        User user1 = new User("login",PASSWORD,TEST_EMAIL_1);
        User user2 = new User("name",PASSWORD,TEST_EMAIL_2);
        assertNull(user1.getId());//null before save
        assertNull(user2.getId());//null before save
        mongoRepository.save(user1);
        mongoRepository.save(user2);
        assertNotNull(user1.getId());
        assertNotNull(user2.getId());
    }

    @Test
    public void testFetchData(){
        /*Test data retrieval*/
        User user = mongoRepository.findUserByEmail(TEST_EMAIL_1);
        assertNotNull(user);
        assertEquals(user.getUsername(),"login");
        /*Get all users, list should only have two*/
        Iterable<User> users = mongoRepository.findAll();
        int count = 0;
        for(User u : users){
            count++;
        }
        assertEquals(count, 2);
    }

    @Test
    public void testDataUpdate(){
        /*Test update*/
        String newName = "updated";
        User user1 = mongoRepository.findUserByEmail(TEST_EMAIL_1);
        user1.setUsername(newName);
        mongoRepository.save(user1);
        User user2 = mongoRepository.findUserByEmail(TEST_EMAIL_1);
        assertNotNull(user2);
        assertEquals(newName, user2.getUsername());
    }

    @After
    public void tearDown() throws Exception {
        this.mongoRepository.deleteAll();
    }
}
