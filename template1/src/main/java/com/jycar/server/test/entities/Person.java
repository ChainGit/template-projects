package com.jycar.server.test.entities;

import lombok.Data;
import org.springframework.stereotype.Repository;

@Repository
@Data
public class Person {

    private long id;
    private String name;
    private int age;
}
