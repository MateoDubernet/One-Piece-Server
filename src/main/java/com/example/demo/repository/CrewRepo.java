package com.example.demo.repository;

import com.example.demo.model.Crew;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CrewRepo extends JpaRepository<Crew, Long> {
    @Query(value = "SELECT * FROM public.crew", nativeQuery = true)
    List<Crew> getAllCrews();

    @Query(value = "SELECT * FROM public.crew WHERE id = :id", nativeQuery = true)
    Crew getCrewById(@Param("id") Long id);
}
