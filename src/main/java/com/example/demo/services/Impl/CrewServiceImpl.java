package com.example.demo.services.Impl;

import com.example.demo.model.Crew;
import com.example.demo.repository.CrewRepo;
import com.example.demo.repository.MemberRepo;
import com.example.demo.services.CrewService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CrewServiceImpl implements CrewService {
    private final CrewRepo crewRepo;
    private final MemberRepo memberRepo;

    public CrewServiceImpl(CrewRepo crewRepo, MemberRepo memberRepo) {
        this.crewRepo = crewRepo;
        this.memberRepo = memberRepo;
    }

    @Override
    public List<Crew> getAllCrews() {
        return crewRepo.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Override
    public Crew getCrewById(Long id) {
        return crewRepo.getCrewById(id);
    }

    @Override
    public void addCrew(Crew crew) {
        crew.setNumberOfMember((short) 0);
        crewRepo.save(crew);
    }

    @Override
    public void updateCrew(Crew crew) {
        crewRepo.save(crew);
    }

    @Transactional
    @Override
    public void deleteCrew(Long id) {
        memberRepo.deleteByCrewId(id);
        crewRepo.deleteById(id);
    }
}
