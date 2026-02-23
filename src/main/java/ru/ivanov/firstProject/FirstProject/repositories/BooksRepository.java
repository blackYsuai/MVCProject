package ru.ivanov.firstProject.FirstProject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ivanov.firstProject.FirstProject.models.Book;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
//    Book findByNameStartingWith(String nameOfBook);
    List<Book> findByNameStartingWith(String nameOfBook);
}
