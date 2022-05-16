package com.repository;

import com.model.TaskNamesList;
import com.model.User;
import com.model.UserTaskInterest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserTaskInterestRepository extends JpaRepository<UserTaskInterest, Long> {

    @Query(value = "SELECT * FROM LeroTaskAllocation.user_task_interest where user_id= ?1", nativeQuery=true)
    List<UserTaskInterest> getUserTaskInterestListForUser(long queryString1);

    @Query(value = "SELECT * FROM LeroTaskAllocation.user_task_interest where task_name_id= ?1", nativeQuery=true)
    List<UserTaskInterest> getUsersInterestedInTask(long queryString1);

}
