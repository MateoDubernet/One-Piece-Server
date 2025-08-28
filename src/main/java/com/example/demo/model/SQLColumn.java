package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
public class SQLColumn {
    private String name;
    private String type;
    private int typeCode;
    private int size;
}
