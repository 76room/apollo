package org.room.apollo.server.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.room.apollo.server.entity.Room;
import org.room.apollo.server.entity.User;
import org.room.apollo.server.security.UserDetailsImpl;
import org.room.apollo.server.service.RoomService;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.doReturn;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class RoomControllerTest {

    private MockMvc mockMvc;

    private UserDetails userDetails;
    private User user;
    @Mock
    private RoomService roomService;

    @Before
    public void reInit() {
        user = new User("user", "email", "password");
        userDetails = new UserDetailsImpl(user);
        mockMvc = MockMvcBuilders
                .standaloneSetup(new RoomController(roomService))
                .build();
    }

    @Test
    public void testCreate() throws Exception {
        doReturn(new Room()).when(roomService).createAndJoin(user);

        mockMvc.perform(post("/room/create")
                .param("title", "Name 1")
                .contentType(MediaType.APPLICATION_JSON).with(user(userDetails)))
                .andExpect(status().isOk());
    }
}
