package com.repository;

import com.model.Task;
import com.model.TaskNamesList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;

public interface TaskNamesListRepository extends JpaRepository<TaskNamesList, Long> {

}
