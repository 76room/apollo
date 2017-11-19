package org.room.apollo.server.repository;

import org.room.apollo.server.entity.Room;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Alexey on 10/9/17.
 */
@Repository
public interface RoomRepository extends MongoRepository<Room, Long> {
    Room findRoomByTitle(String title);
    List<Room> findRoomByStatus(Room.Status status);
}
