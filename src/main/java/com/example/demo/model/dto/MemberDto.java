package com.example.demo.model.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
    private Long id;

    private String name;

    private List<String> stations;

    private List<String> abilities;

    private Long bounty;

    private Short age;

    private List<String> weapons;

    private Long crewId;
}
