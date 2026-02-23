package ru.ivanov.firstProject.FirstProject.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_book")
    private int id_book;

    @Column(name = "name")
    @NotEmpty(message = "Name of book shouldn't be empty")
    private String name;

    @Column(name = "author")
    @NotEmpty(message = "Author shouldn't be empty")
    private String author;

    @Column(name = "year")
    @Min(value = 1, message = "Год должен быть больше 0")
    @Max(value = 2026, message = "Год не может быть больше текущего")
    private int year;

    @Column(name = "timeofget")
    private LocalDateTime getAt;

    @ManyToOne
    @JoinColumn(name = "id_person", referencedColumnName = "id")
    private Person owner;

    @Transient
    private boolean isTenDaysOver;

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

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public LocalDateTime getGetAt() {
        return getAt;
    }

    public void setGetAt(LocalDateTime getAt) {
        this.getAt = getAt;
    }

    public boolean isTenDaysOver() {
        return isTenDaysOver;
    }

    public void setTenDaysOver(boolean tenDaysOver) {
        isTenDaysOver = tenDaysOver;
    }
}
