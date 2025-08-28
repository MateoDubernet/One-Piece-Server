package com.example.demo.repository;

import com.example.demo.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface MemberRepo extends JpaRepository<Member, Long> {
    @Query(value = "SELECT * FROM public.member WHERE crew_id = :crewId", nativeQuery = true)
    List<Member> getMemberByCrewId(@Param("crewId") Long crewId);

    @Query(value = "SELECT * FROM public.member WHERE id = :id", nativeQuery = true)
    Member getMemberById(@Param("id") Long id);

    @Modifying
    @Query(value = "DELETE FROM public.member WHERE crew_id = :crewId", nativeQuery = true)
    void deleteByCrewId(@Param("crewId") Long crew_id);
}
