package com.theZ.dotoring.app.mento.repository;

import com.theZ.dotoring.app.mento.model.Mento;
import com.theZ.dotoring.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MentoRepository extends JpaRepository<Mento,Long> {

    @Query("select m from Mento m where m.memberAccount.id = :memberAccountId ")
    Optional<Mento> findMentoByMemberAccountId(Long memberAccountId);

    @Query("SELECT M FROM Mento M JOIN FETCH M.profile WHERE M.mentoId = :mentoId")
    Optional<Mento> findMentoWithProfileUsingFetchJoinByMentoId(@Param("mentoId") Long mentoId);

    @Query("SELECT distinct M FROM Mento M JOIN FETCH M.profile JOIN FETCH M.memberMajors WHERE M.mentoId in :mentoIds and M.status = :status")
    List<Mento> findMentosWithProfileAndFieldsAndMajorsUsingFetchJoinByMentoId(@Param("mentoIds") List<Long> mentoIds, @Param("status") Status status);


    @Query("SELECT M FROM Mento M JOIN FETCH M.profile JOIN FETCH M.memberMajors WHERE M.mentoId = :mentoId")
    Optional<Mento> findMentoWithProfileAndMajorsUsingFetchJoinByMentoId(@Param("mentoId") Long mentoId);

    @Query("SELECT M FROM Mento M WHERE M.status = :status")
    Page<Mento> findMentosByStatus(@Param("status") Status status, Pageable pageable);

}
