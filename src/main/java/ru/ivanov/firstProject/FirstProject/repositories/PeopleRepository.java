package ru.ivanov.firstProject.FirstProject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ivanov.firstProject.FirstProject.models.Person;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
}
