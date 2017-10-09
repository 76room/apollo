package org.room.apollo.server.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.room.apollo.server.entity.Room;
import org.room.apollo.server.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by Alexey on 10/9/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RoomRepositoryTest {

    @Autowired
    private RoomRepository mongoRepository;

    private String TEST_EMAIL_1 = "1@mail.ru";
    private String TEST_EMAIL_2 = "2@mail.ru";
    private String PASSWORD = "password";
    private List<User> users;

    @Before
    public void setUp() throws Exception {
        User user1 = new User("login",PASSWORD,TEST_EMAIL_1);
        User user2 = new User("name",PASSWORD,TEST_EMAIL_2);
        users = com.sun.tools.javac.util.List.of(user1,user2);
        Room room = new Room("test", users , null);
        assertNull(room.getId());//null before save
        this.mongoRepository.save(room);
        assertNotNull(room);
    }

    @Test
    public void testFetchData(){
        /*Test data retrieval*/
        Room room = mongoRepository.findRoomByTitle("test");
        assertNotNull(room);
        assertEquals(room.getUsers(),users);
        /*Get all users, list should only have two*/
        List<Room> rooms = mongoRepository.findAll();
        assertEquals(rooms.size(), 1);
    }

    @Test
    public void testDataUpdate(){
        /*Test update*/
        String newName = "updated";
        Room first = mongoRepository.findRoomByTitle("test");
        first.setTitle(newName);
        mongoRepository.save(first);
        Room second = mongoRepository.findRoomByTitle("test");
        assertNotNull(second);
        assertEquals(newName, second.getTitle());
    }

    @After
    public void tearDown() throws Exception {
        this.mongoRepository.deleteAll();
    }
}
