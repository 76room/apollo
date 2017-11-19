package org.room.apollo.server.repository;

import org.room.apollo.server.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Alexey on 10/7/17.
 */
@Repository
public interface UserRepository extends MongoRepository<User, Long> {

    User findUserByUsername(String username);

    User findUserByEmail(String email);

}
