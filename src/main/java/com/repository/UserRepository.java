package com.repository;

import com.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;//

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT * FROM LeroTaskAllocation.user where email= ?1 and password= ?2", nativeQuery=true)
    User loginUser(String queryString1, String queryString2);

    @Query(value = "SELECT * FROM LeroTaskAllocation.user where email= ?1", nativeQuery=true)
    User getUserByEmail(String queryString1);

}
