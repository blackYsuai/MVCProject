package ru.ivanov.firstProject.FirstProject.models;

import jakarta.validation.constraints.Pattern;

public class Person {
    private int id;

    @Pattern(regexp = "[А-ЯЁ][а-яё]+ [А-ЯЁ][а-яё]+ [А-ЯЁ][а-яё]+", message = "Please write your full name right (Example: Иванов Игорь Юрьевич)")
    private String full_name;

    private int year;

    public Person(){}

    public Person(String full_name, int year) {
        this.full_name = full_name;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

}
