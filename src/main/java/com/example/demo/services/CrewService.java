package com.example.demo.services;

import com.example.demo.model.Crew;

import java.util.List;

public interface CrewService {
    List<Crew> getAllCrews();

    Crew getCrewById(Long id);

    void addCrew(Crew crew);

    void updateCrew(Crew crew);

    void deleteCrew(Long id);
}
