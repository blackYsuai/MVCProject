package ru.ivanov.firstProject.FirstProject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ivanov.firstProject.FirstProject.models.Book;
import ru.ivanov.firstProject.FirstProject.models.Person;
import ru.ivanov.firstProject.FirstProject.repositories.PeopleRepository;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepositor) {
        this.peopleRepository = peopleRepositor;
    }

    public List<Person> showAllPeople() {
        return peopleRepository.findAll();
    }

    @Transactional
    public void addPerson(Person person) {
        peopleRepository.save(person);
    }

    public Person index(int id) {
        return peopleRepository.findById(id).orElse(null);
    }

    @Transactional
    public void updatePerson(Person newPerson, int id) {
        newPerson.setId(id);
        peopleRepository.save(newPerson);
    }

    @Transactional
    public void deletePerson(int id) {
        peopleRepository.deleteById(id);
    }

    @Transactional
    public List<Book> findBook(int id) {
        Person person = peopleRepository.findById(id).orElse(null);
        List<Book> books = person.getBooks();
        for (Book book : books) {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime bookGetTime = book.getGetAt();
            long days = ChronoUnit.DAYS.between(bookGetTime, now);
            book.setTenDaysOver(days > 10);
        }
        return books;
    }
}
