package org.room.apollo.server.controller;

import org.room.apollo.server.dto.ExceptionResponse;
import org.room.apollo.server.dto.RoomForm;
import org.room.apollo.server.entity.Room;
import org.room.apollo.server.security.UserDetailsImpl;
import org.room.apollo.server.service.RoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController()
@RequestMapping("/room")
public class RoomController {

    private final RoomService roomService;

    private static final Logger LOG = LoggerFactory.getLogger(RoomController.class);

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping("/create-empty")
    public Room createAndJoin() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Room room = roomService.createAndJoin(userDetails.getUser());
        LOG.info("Created room: " + room.getId() );
        return room;
    }

    @PostMapping("/create")
    public Room createAndJoinByForm(@Valid RoomForm roomForm) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Room room = roomService.createAndJoin(userDetails.getUser(), roomForm);
        LOG.info("Created room: " + room.getId() );
        return room;
    }

    @PostMapping("/join/{id}")
    public ResponseEntity<Object> join(@PathVariable long id){
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean result = roomService.join(id,userDetails.getUser());
        if (result) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(new ExceptionResponse("Room doesn't exists"), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/leave/{id}")
    public ResponseEntity<Object> leave(@PathVariable long id){
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean result = roomService.leave(id,userDetails.getUser());
        if (result) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(new ExceptionResponse("Room doesn't exists"), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/all")
    public List<Room> getAllRooms(){
        return roomService.getPublicRooms();
    }

    //invite
    //notifications
}
