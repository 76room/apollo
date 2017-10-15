package org.room.apollo.server.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.room.apollo.server.entity.Track;
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
public class TrackRepositoryTest {

    @Autowired
    private TrackRepository mongoRepository;

    @Before
    public void setUp() throws Exception {
        Track track = new Track("track1","http..");
        assertNull(track.getId());//null before save
        mongoRepository.save(track);
        assertNotNull(track.getId());
    }

    @Test
    public void testFetchData(){
        /*Test data retrieval*/
        Track obj = mongoRepository.findTrackByTitle("track1");
        assertNotNull(obj);
        assertEquals(obj.getStreamingUrl(),"http..");
        /*Get all users, list should only have two*/
        List<Track> rooms = mongoRepository.findAll();
        assertEquals(rooms.size(), 1);
    }

    @After
    public void tearDown() throws Exception {
        this.mongoRepository.deleteAll();
    }
}
