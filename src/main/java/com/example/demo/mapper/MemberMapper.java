package com.example.demo.mapper;

import com.example.demo.model.Member;
import com.example.demo.model.dto.MemberDto;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MemberMapper {
    public MemberDto toMemberDto(Member member) {
        return MemberDto.builder()
                .id(member.getId())
                .name(member.getName())
                .stations(stringToList(member.getStation()))
                .abilities(stringToList(member.getAbilitie()))
                .bounty(member.getBounty())
                .age(member.getAge())
                .weapons(stringToList(member.getWeapon()))
                .crewId(member.getCrewId())
                .build();
    }

    public Member toMember(MemberDto memberDto) {
        return Member.builder()
                .id(memberDto.getId())
                .name(memberDto.getName())
                .station(ListToString(memberDto.getStations()))
                .abilitie(ListToString(memberDto.getAbilities()))
                .bounty(memberDto.getBounty())
                .age(memberDto.getAge())
                .weapon(ListToString(memberDto.getWeapons()))
                .crewId(memberDto.getCrewId())
                .build();
    }

    private List<String> stringToList(String input) {
        if (input == null || input.isBlank()) {
            return List.of();
        }

        String[] parts = input.split("\\s*,\\s*");
        return Arrays.stream(parts)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }

    private static String ListToString(List<String> input) {
        if (input == null || input.isEmpty()) {
            return "";
        }
        return input.stream()
                .map(String::trim)
                .collect(Collectors.joining(", "));
    }
}
