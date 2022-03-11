package com.study.board2.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User,Integer>{

   // @Query(value = "select u.user_id,u.user_password from user u where u.user_id = :user_id and u.user_password = :user_password",
    //        nativeQuery = true)
    @Query(value ="select * from user u where u.user_id = :id and u.user_password = :password",nativeQuery = true)
    User findById(@Param("id") String id,@Param("password") String password);
}
