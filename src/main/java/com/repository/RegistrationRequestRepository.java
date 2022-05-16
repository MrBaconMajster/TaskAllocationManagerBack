package com.repository;

import com.model.RegistrationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RegistrationRequestRepository extends JpaRepository<RegistrationRequest, Long> {

    @Query(value = "SELECT * FROM LeroTaskAllocation.registration_request where email= ?1", nativeQuery=true)
    RegistrationRequest getRegistrationRequestByEmail(String queryString1);

    @Query(value = "SELECT * FROM LeroTaskAllocation.registration_request where request_status= 'Pending'", nativeQuery=true)
    List<RegistrationRequest> getAllPendingRegistrationRequests();
}
