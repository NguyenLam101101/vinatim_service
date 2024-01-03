package com.example.recruitment2.Repository;

import com.example.recruitment2.Entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.awt.print.Pageable;
import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {
    public User findUserByVNeId(String VNeId);
    public User findUserByEmail(String email);
    public User findUserByPhone(String phone);
    public User findUserByVNeIdOrEmailOrPhone(String VNeId, String email, String phone);
}
