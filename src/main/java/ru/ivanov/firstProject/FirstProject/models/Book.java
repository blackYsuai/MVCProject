package ru.ivanov.firstProject.FirstProject.models;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public class Book {
    private int id_book;
    private Integer id_person;

    @NotEmpty(message = "Name of book shouldn't be empty")
    private String name;

    @NotEmpty(message = "Author shouldn't be empty")
    private String author;

    @Min(value = 1, message = "Год должен быть больше 0")
    @Max(value = 2026, message = "Год не может быть больше текущего")
    private int year;

    public Book() {
    }

    public Book(String name, String author, int year) {
        this.name = name;
        this.author = author;
        this.year = year;
    }

    public int getId_book() {
        return id_book;
    }

    public Integer getId_person() {
        return id_person;
    }

    public void setId_person(Integer id_person) {
        this.id_person = id_person;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setId_book(int id_book) {
        this.id_book = id_book;
    }
}
