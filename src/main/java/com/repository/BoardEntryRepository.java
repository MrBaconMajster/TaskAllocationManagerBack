package com.repository;

import com.model.BoardEntry;
import com.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BoardEntryRepository extends JpaRepository<BoardEntry, Long> {

    @Query(value = "SELECT * FROM LeroTaskAllocation.board_entry where createdBy= ?1", nativeQuery=true)
    BoardEntry getBoardEntryByUserID(long queryString1);

}
