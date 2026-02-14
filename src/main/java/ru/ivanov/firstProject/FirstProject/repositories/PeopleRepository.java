package ru.ivanov.firstProject.FirstProject.repositories;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.ivanov.firstProject.FirstProject.models.Person;

import java.util.List;
import java.util.Optional;

@Repository
public class PeopleRepository {
    private final JdbcTemplate jdbcTemplate;

    private final static String SELECT_ALL = "SELECT * FROM person";
    private final static String SELECT_BY_ID = "SELECT * FROM person WHERE id=?";
    private final static String INSERT_PERSON = "INSERT INTO person(full_name, year) VALUES(?,?)";
    private final static String UPDATE_PERSON = "UPDATE person SET full_name=?, year=? WHERE id=?";
    private final static String DELETE_PERSON = "DELETE FROM person WHERE id=?";

    public PeopleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> showAllPeople() {
        return jdbcTemplate.query(SELECT_ALL, new BeanPropertyRowMapper<>(Person.class));
    }

    public void addPerson(Person person) {
        jdbcTemplate.update(INSERT_PERSON, person.getFull_name(), person.getYear());
    }

    public Person index(int id) {
        return jdbcTemplate.queryForObject(SELECT_BY_ID, new BeanPropertyRowMapper<>(Person.class), id);
    }

    public void updatePerson(Person newPerson, int id) {
        jdbcTemplate.update(UPDATE_PERSON, newPerson.getFull_name(), newPerson.getYear(), id);
    }

    public void deletePerson(int id) {
        jdbcTemplate.update(DELETE_PERSON, id);
    }
}
