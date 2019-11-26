package com.pojo.jpa;

import javax.persistence.*;

/**
 * @author AlbertXe
 * @date 2019-11-07 14:52
 */
@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private Integer age;
}
