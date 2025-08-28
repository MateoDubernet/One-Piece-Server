package com.example.demo.controller;

import com.example.demo.model.Crew;
import com.example.demo.services.CrewService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/crew")
@CrossOrigin("http://localhost:4200/")
public class CrewController {
    private final CrewService crewService;

    public CrewController(CrewService crewService) {
        this.crewService = crewService;
    }

    @GetMapping
    public List<Crew> getAllCrews(){
        return crewService.getAllCrews();
    }

    @GetMapping("/{id}")
    public Crew getCrewById(@PathVariable("id") Long id){
        return crewService.getCrewById(id);
    }

    @PostMapping
    public void addCrew(@RequestBody Crew crew){
        crewService.addCrew(crew);
    }

    @PutMapping
    public void updateCrew(@RequestBody Crew crew){
        crewService.updateCrew(crew);
    }

    @DeleteMapping("/{id}")
    public void deleteCrew(@PathVariable("id") Long id){
        crewService.deleteCrew(id);
    }
}
