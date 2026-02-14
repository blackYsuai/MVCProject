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

@Controller
@RequestMapping("/book")
public class BookController {
    private final BookRepository bookRepository;
    private final PeopleRepository peopleRepository;

    public BookController(BookRepository bookRepository, PeopleRepository peopleRepository) {
        this.bookRepository = bookRepository;
        this.peopleRepository = peopleRepository;
    }

    @GetMapping()
    public String showBooks(Model model) {
        model.addAttribute("books", bookRepository.showAllBooks());
        return "books/show";
    }

    @GetMapping("add")
    public String showAddingMenu(@ModelAttribute Book book) {
        return "books/add";
    }

    @PostMapping()
    public String addBook(@ModelAttribute("book") @Valid Book book,
                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "books/add"; // возврат на форму с ошибками
        }
        bookRepository.addBook(book);
        return "redirect:/book";
    }

    @GetMapping("{id}")
    public String bookInformation(Model model, @PathVariable int id, @ModelAttribute Person person) {
        model.addAttribute("people", peopleRepository.showAllPeople());
        model.addAttribute("book", bookRepository.index(id));
        Book book = bookRepository.index(id);
        if (book.getId_person() != null) {
            Person person1 = peopleRepository.index(book.getId_person());
            model.addAttribute("person", person1);
        }
        return "books/book";
    }

    @GetMapping("{id}/edit")
    public String editingMenu(Model model, @PathVariable int id) {
        model.addAttribute("book", bookRepository.index(id));
        return "books/edit";
    }


    @PatchMapping("{id}")
    public String editBook(@ModelAttribute("book") @Valid Book book,
                           BindingResult bindingResult,
                           @PathVariable int id) {
        if (bindingResult.hasErrors()) {
            return "books/edit";
        }
        bookRepository.updateBook(id, book);
        return "redirect:/book";
    }

    @DeleteMapping("{id}")
    public String deleteBook(@PathVariable int id) {
        bookRepository.deleteBook(id);
        return "redirect:/book";
    }


    @PatchMapping("{id}/edit/person")
    public String updateBookOwner(@ModelAttribute("book") Book book,
                                  @PathVariable int id,
                                  @RequestParam("personId") int personId) {
        bookRepository.updatePerson(personId, id);
        return "redirect:/book";
    }

    @DeleteMapping("{id}/delete/person")
    public String deleteBookFromPerson(@PathVariable int id) {
        bookRepository.deletePersonBook(id);
        return "redirect:/book";
    }

}
