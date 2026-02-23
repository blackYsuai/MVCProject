package ru.ivanov.firstProject.FirstProject.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ivanov.firstProject.FirstProject.models.Book;
import ru.ivanov.firstProject.FirstProject.models.Person;
import ru.ivanov.firstProject.FirstProject.repositories.BooksRepository;
import ru.ivanov.firstProject.FirstProject.repositories.PeopleRepository;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BooksRepository booksRepository;
    private final PeopleRepository peopleRepository;

    @Autowired
    public BookService(BooksRepository booksRepository, PeopleRepository peopleRepository) {
        this.booksRepository = booksRepository;
        this.peopleRepository = peopleRepository;
    }

    public List<Book> showAllBooks() {
        return booksRepository.findAll();
    }

    public List<Book> showAllBooks(boolean sort_by_year) {
        if (sort_by_year) {
            return booksRepository.findAll(Sort.by("year"));
        }
        return showAllBooks();
    }

    public List<Book> showAllBooks(int page, int books_per_page) {
        Pageable pageable = PageRequest.of(page, books_per_page);
        Page<Book> booksPage = booksRepository.findAll(pageable);
        return booksPage.getContent();
    }

    public List<Book> showAllBooks(int page, int books_per_page, boolean sort_by_year) {
        if (sort_by_year) {
            Pageable pageable = PageRequest.of(page, books_per_page, Sort.by("year"));
            Page<Book> booksPage = booksRepository.findAll(pageable);
            return booksPage.getContent();
        }
        return showAllBooks(page, books_per_page);
    }

//
//    public Book findBookByName(String nameOfBook){
//        Book book = booksRepository.findByNameStartingWith(nameOfBook);
//        if (book!=null){
//            Hibernate.initialize(book.getOwner());
//        }
//        return book;
//    }

    public List<Book> findBooksByName(String nameOfBook){
        List<Book> books = booksRepository.findByNameStartingWith(nameOfBook);

        if (!books.isEmpty()) {
            for (Book book : books) {
                Hibernate.initialize(book.getOwner());
            }
        }
        return books;
    }
    @Transactional
    public void addBook(Book book) {
        booksRepository.save(book);
    }

    public Book index(int id) {
        Book book = booksRepository.findById(id).orElse(null);
        assert book != null;
        Hibernate.initialize(book.getOwner());
        return book;
    }

    @Transactional
    public void updateBook(int id, Book updateBook) {
        updateBook.setId_book(id);
        booksRepository.save(updateBook);
    }

    @Transactional
    public void deleteBook(int id) {
        booksRepository.deleteById(id);
    }

    @Transactional
    public void updatePerson(int idPerson, int idBook) {
        Book book = booksRepository.findById(idBook)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        Person person = peopleRepository.findById(idPerson)
                .orElseThrow(() -> new RuntimeException("Person not found"));

        // Обновляем владеющую сторону
        book.setOwner(person);
        book.setGetAt(LocalDateTime.now());

        // Обновляем обратную ссылку (для целостности в Java)
        if (!person.getBooks().contains(book)) {
            person.getBooks().add(book);
        }
    }

    @Transactional
    public void deletePersonBook(int id) {
        Book book = booksRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        Person owner = book.getOwner();
        if (owner != null) {
            owner.getBooks().remove(book); // обновляем обратную ссылку
            book.setOwner(null);           // обнуляем FK
        }
    }

    public Person getPerson(int id) {
        return Objects.requireNonNull(booksRepository.findById(id).orElse(null)).getOwner();
    }
}
