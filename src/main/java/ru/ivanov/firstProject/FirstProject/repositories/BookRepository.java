package ru.ivanov.firstProject.FirstProject.repositories;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.ivanov.firstProject.FirstProject.models.Book;

import java.util.List;

@Repository
public class BookRepository {
    private final JdbcTemplate jdbcTemplate;

    private final static String SELECT_ALL = "SELECT * FROM book";
    private final static String SELECT_BY_ID = "SELECT * FROM book WHERE id_book=?";
    private final static String INSERT_BOOK = "INSERT INTO book(name, author, year) VALUES(?,?,?)";
    private final static String UPDATE_BOOK = "UPDATE book SET name=?, author=?, year=? WHERE id_book=?";
    private final static String DELETE_BOOK = "DELETE FROM book WHERE id_book=?";
    private final static String UPDATE_PERSON = "UPDATE book SET id_person=? WHERE id_book=?";
    private final static String DELETE_PERSON = "UPDATE book SET id_person=null WHERE id_book=?";
    private final static String FIND_BOOK = "SELECT name, author, year FROM book WHERE id_person=?";

    public BookRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> showAllBooks() {
        return jdbcTemplate.query(SELECT_ALL, new BeanPropertyRowMapper<>(Book.class));
    }

    public void addBook(Book book) {
        jdbcTemplate.update(INSERT_BOOK, book.getName(), book.getAuthor(), book.getYear());
    }

    public Book index(Integer id) {
        return jdbcTemplate.queryForObject(SELECT_BY_ID, new BeanPropertyRowMapper<>(Book.class), id);
    }

    public void updateBook(Integer id, Book updateBook) {
        jdbcTemplate.update(UPDATE_BOOK, updateBook.getName(), updateBook.getAuthor(), updateBook.getYear(), id);
    }

    public void deleteBook(Integer id) {
        jdbcTemplate.update(DELETE_BOOK, id);
    }

    public void updatePerson(Integer idPerson, int idBook){
        jdbcTemplate.update(UPDATE_PERSON, idPerson, idBook);
    }

    public void deletePersonBook(int id){
        jdbcTemplate.update(DELETE_PERSON,id);
    }

    public List<Book> findBook(int id){
        return jdbcTemplate.query(FIND_BOOK, new BeanPropertyRowMapper<>(Book.class), id);
    }
}
