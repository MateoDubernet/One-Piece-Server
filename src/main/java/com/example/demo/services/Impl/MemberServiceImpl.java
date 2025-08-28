package com.example.demo.services.Impl;

import com.example.demo.mapper.MemberMapper;
import com.example.demo.model.Crew;
import com.example.demo.model.Member;
import com.example.demo.model.dto.MemberDto;
import com.example.demo.repository.CrewRepo;
import com.example.demo.repository.MemberRepo;
import com.example.demo.services.MemberService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberServiceImpl implements MemberService {
    private final MemberRepo memberRepo;
    private final CrewRepo crewRepo;
    private final MemberMapper memberMapper;

    public MemberServiceImpl(MemberRepo memberRepo, CrewRepo crewRepo, MemberMapper memberMapper) {
        this.memberRepo = memberRepo;
        this.crewRepo = crewRepo;
        this.memberMapper = memberMapper;
    }

    @Override
    public List<MemberDto> getMembersByCrewId(Long crewId) {
        List<Member> members = memberRepo.getMemberByCrewId(crewId);
        return members
                .stream()
                .map(memberMapper::toMemberDto)
                .collect(Collectors.toList());
    }

    @Override
    public Member getMemberById(Long id) {
        return memberRepo.getMemberById(id);
    }

    @Override
    public void addMember(MemberDto memberDto) {
        saveMember(memberDto);
        updateCrewNumberOfMember(memberDto.getCrewId(), true);
    }

    @Override
    public void updateMember(MemberDto memberDto) {
        saveMember(memberDto);
    }

    @Override
    public void deleteMember(Long id) {
        Member member = memberRepo.getMemberById(id);
        memberRepo.deleteById(id);
        updateCrewNumberOfMember(member.getCrewId(), false);
    }

    private void saveMember(MemberDto memberDto) {
        Member member = memberMapper.toMember(memberDto);
        memberRepo.save(member);
    }

    private void updateCrewNumberOfMember(Long crewId, boolean isNewMember) {
        Crew crew = crewRepo.getCrewById(crewId);
        Short numberOfMember = crew.getNumberOfMember();

        if (isNewMember) {
            crew.setNumberOfMember((short) (numberOfMember + 1));
        } else {
            crew.setNumberOfMember((short) (numberOfMember - 1));
        }

        crewRepo.save(crew);
    }
}
