package com.leelee.myhome.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    // NotNull 어노테이션 javax.어쩌구 임포트 할 수 없었음.(com.어쩌구만뜸) 스프링버전 차이때문
    // pom.xml에 validation maven 추가해야함
    @Size(min=2, max=30, message = "제목은 2자이상 30자 이하여야합니다.")
    private String title;

    private String content;
}
