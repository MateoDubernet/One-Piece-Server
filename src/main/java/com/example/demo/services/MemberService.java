package com.example.demo.services;

import com.example.demo.model.Member;
import com.example.demo.model.dto.MemberDto;

import java.util.List;

public interface MemberService {
    List<MemberDto> getMembersByCrewId(Long crewId);

    Member getMemberById(Long id);

    void addMember(MemberDto memberDto);

    void updateMember(MemberDto memberDto);

    void deleteMember(Long id);
}
