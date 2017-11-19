package org.room.apollo.server.service;

import org.apache.commons.text.RandomStringGenerator;
import org.room.apollo.server.dto.RoomForm;
import org.room.apollo.server.entity.Room;
import org.room.apollo.server.entity.User;
import org.room.apollo.server.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public Room createAndJoin(User user) {
        Room room = new Room();
        room.setTitle(new RandomStringGenerator.Builder().build().generate(10));
        room.setAdmin(user);
        room.setStatus(Room.Status.PUBLIC);
        roomRepository.save(room);
        return room;
    }

    public Room createAndJoin(User user, RoomForm form) {
        Room room = new Room();
        room.setTitle(form.getTitle());
        room.setDescription(form.getDescription());
        room.setStatus(form.getStatus());
        room.setAdmin(user);
        roomRepository.save(room);
        return room;
    }

    public boolean leave(long roomId, User user) {
        Room room = roomRepository.findOne(roomId);
        if (room != null) {
            room.getUsers().remove(user);
            if (room.getUsers().size() == 0) {
                roomRepository.delete(room);
            } else {
                roomRepository.save(room);
            }
            return true;
        }
        return false;
    }

    public boolean join(long roomId, User user) {
        Room room = roomRepository.findOne(roomId);
        if (room != null) {
            room.getUsers().add(user);
            roomRepository.save(room);
            return true;
        }
        return false;
    }

    public List<Room> getPublicRooms() {
        return roomRepository.findRoomByStatus(Room.Status.PUBLIC);
    }
}
