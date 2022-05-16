package com.repository;

import com.model.Request;
import com.model.Task;
import com.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;

public interface RequestRepository extends JpaRepository<Request, Long> {
    @Query(value = "SELECT * FROM LeroTaskAllocation.request where receiver_id= ?1", nativeQuery=true)
    ArrayList<Request> getRequestsForUser(long queryString1);

    @Query(value = "SELECT * FROM LeroTaskAllocation.request where receiver_id= ?1 and request_state= ?2", nativeQuery=true)
    ArrayList<Request> getPendingRequestsForUser(long queryString1, String queryString2);

    @Query(value = "SELECT * FROM LeroTaskAllocation.request where receiver_id= ?1 and task_id= ?2 and request_state = 'Pending'", nativeQuery=true)
    Request getDuplicateRequest(long queryString1, long queryString2);

    @Query(value = "SELECT * FROM LeroTaskAllocation.request WHERE task_id = ?1 and receiver_id <> ?2", nativeQuery=true)
    ArrayList<Request> setOtherRequestsToRejected(long queryString1, long queryString2);

    @Query(value = "SELECT * FROM LeroTaskAllocation.request where sender_id= ?1 and request_state = 'Pending'", nativeQuery=true)
    ArrayList<Request>  getPendingRequestsSentByUser(long queryString1);
}
