package com.example.demo.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "crew", schema = "public")
public class Crew {
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "ship_name")
    private String ship;

    @Column(name = "member_max")
    private Short memberMax;

    private Short numberOfMember;
}
