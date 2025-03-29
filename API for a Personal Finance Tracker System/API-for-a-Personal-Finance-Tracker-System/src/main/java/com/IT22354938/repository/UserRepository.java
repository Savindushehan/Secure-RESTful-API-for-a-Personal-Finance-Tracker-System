package com.IT22354938.repository;

import com.IT22354938.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

//public interface UserRepository extends MongoRepository<User,String> {
//    Optional<User> findByNic(String nic);
//    Optional<User> findByUserName(String userName);
//
//}


public interface UserRepository extends MongoRepository<User,String> {
    Optional<User> findByNic(String nic);
  //  Optional<User> findByUserName(String userName);
    User findByUserName(String userName);
}
