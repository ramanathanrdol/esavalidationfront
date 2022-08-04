package com.flalottery.esavalidation.repository;

import com.flalottery.esavalidation.model.MemberTicketEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.io.Serializable;

public interface EntryCountRepository extends JpaRepository<MemberTicketEntry, Serializable> {
    @Query(value = "Select count(*) from MEMBER_TICKET_ENTRY where MEMBER_ID_FK= :playerId and SND_GAME_ID_FK = :gameNumber and to_char(CREATE_DATE,'MM/DD/YYYY') = :createdDate", nativeQuery = true)
    Long countByPlayerId(@Param("playerId") int playerId, @Param("gameNumber") String gameNumber, @Param("createdDate") String createdDate);
}
