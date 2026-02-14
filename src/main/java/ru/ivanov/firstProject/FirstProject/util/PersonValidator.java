package ru.ivanov.firstProject.FirstProject.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.ivanov.firstProject.FirstProject.models.Person;
import ru.ivanov.firstProject.FirstProject.repositories.PeopleRepository;

import java.time.LocalDate;

@Component
public class PersonValidator implements Validator {
    private final PeopleRepository peopleRepository;

    public PersonValidator(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        int currentYear = LocalDate.now().getYear();
        //Проверяем год рожедния
        if (person.getYear()<1900|| person.getYear()>currentYear){
            errors.rejectValue("year","", "Write right year");
        }
    }
}
