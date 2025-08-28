package com.example.demo.controller;

import com.example.demo.model.Member;
import com.example.demo.model.dto.MemberDto;
import com.example.demo.services.MemberService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/member")
@CrossOrigin("http://localhost:4200/")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("crew/{crewId}")
    public List<MemberDto> getMembersByCrewId(@PathVariable("crewId") Long crewId){
        return memberService.getMembersByCrewId(crewId);
    }

    @PostMapping
    public void addMember(@RequestBody MemberDto memberDto){
        memberService.addMember(memberDto);
    }

    @PutMapping
    public void updateMember(@RequestBody MemberDto memberDto){
        memberService.updateMember(memberDto);
    }

    @DeleteMapping
    public void deleteMember(@RequestParam("id") Long id){
        memberService.deleteMember(id);
    }
}
