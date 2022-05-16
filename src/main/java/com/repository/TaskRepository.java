package com.repository;

import com.model.BoardEntry;
import com.model.Task;
import com.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query(value = "SELECT * FROM LeroTaskAllocation.task where created_by= ?1", nativeQuery=true)
    ArrayList<Task> getTasksByUserID(long queryString1);

    @Query(value = "SELECT * FROM LeroTaskAllocation.task where assigned_to= ?1", nativeQuery=true)
    ArrayList<Task> getTasksByAssignedUserID(long queryString1);
}
