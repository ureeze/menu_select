package com.example.menu.entity;

import lombok.Getter;

@Getter
public enum MenuType {
    DONKKAS("돈까스"),
    NOODLE("면"),
    BAEKBAN("백반"),
    SSAL_RAMEN("쌀국수"),
    GAMJA("감자탕"),
    HAMBURGER("햄버거"),
    DUPBAP("덮밥");

    private String name;

    MenuType(String name) {
        this.name = name;
    }


}
