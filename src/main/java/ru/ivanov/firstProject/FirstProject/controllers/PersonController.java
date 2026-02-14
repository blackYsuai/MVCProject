package ru.ivanov.firstProject.FirstProject.controllers;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.ivanov.firstProject.FirstProject.models.Book;
import ru.ivanov.firstProject.FirstProject.models.Person;
import ru.ivanov.firstProject.FirstProject.repositories.BookRepository;
import ru.ivanov.firstProject.FirstProject.repositories.PeopleRepository;
import ru.ivanov.firstProject.FirstProject.util.PersonValidator;

import java.util.List;

@Controller
@RequestMapping("/people")
public class PersonController {
    private final PeopleRepository peopleRepository;
    private final BookRepository bookRepository;
    private final PersonValidator personValidator;

    public PersonController(PeopleRepository peopleRepository, BookRepository bookRepository, PersonValidator personValidator) {
        this.peopleRepository = peopleRepository;
        this.bookRepository = bookRepository;
        this.personValidator = personValidator;
    }

    @GetMapping()
    public String showPeople(Model model) {
        model.addAttribute("people", peopleRepository.showAllPeople());
        return "humans/show";
    }

    @GetMapping("add")
    public String showAddingMenu(@ModelAttribute Person person) {
        return "humans/add";
    }

    @PostMapping()
    public String addPerson(@ModelAttribute("person") @Valid Person person,
             BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()){
            return "humans/add";
        }
        peopleRepository.addPerson(person);
        return "redirect:/people";
    }

    @GetMapping("{id}")
    public String personInformation(Model model, @PathVariable int id) {
        model.addAttribute("person", peopleRepository.index(id));
        List<Book>books = bookRepository.findBook(id);
        if (!books.isEmpty()){
            model.addAttribute("books", books);
        }else {
            model.addAttribute("books", null);
        }
        return "humans/person";
    }

    @GetMapping("{id}/edit")
    public String editingMenu(Model model, @PathVariable int id) {
        model.addAttribute("person", peopleRepository.index(id));
        return "humans/edit";
    }

    @PatchMapping("{id}")
    public String editPerson(@ModelAttribute("person") @Valid Person person,
                             BindingResult bindingResult,
                             @PathVariable int id) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()){
            return "humans/edit";
        }
        peopleRepository.updatePerson(person, id);
        return "redirect:/people";
    }

    @DeleteMapping("{id}")
    public String deletePerson(@PathVariable int id){
        peopleRepository.deletePerson(id);
        return "redirect:/people";
    }
}
