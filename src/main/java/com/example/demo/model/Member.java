package com.example.demo.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "member", schema = "public")
public class Member {
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String station;

    @Column(name = "abilities")
    private String abilitie;

    private Long bounty;

    private Short age;

    private String weapon;

    private Long crewId;
}
