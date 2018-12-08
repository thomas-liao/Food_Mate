package com.oose.group18.Repository;

import com.oose.group18.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
    @Query("select u from User u where u.userName = :userName and u.password = :password")
    User login(@Param("userName") String userName, @Param("password") String password);
}
