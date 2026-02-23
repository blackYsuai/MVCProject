package ru.ivanov.firstProject.FirstProject.controllers;

import jakarta.validation.Valid;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.ivanov.firstProject.FirstProject.models.Book;
import ru.ivanov.firstProject.FirstProject.models.Person;
import ru.ivanov.firstProject.FirstProject.services.BookService;
import ru.ivanov.firstProject.FirstProject.services.PeopleService;

import java.util.List;

@Controller
@RequestMapping("/book")
public class BookController {
    private final BookService bookService;
    private final PeopleService peopleService;

    public BookController(BookService bookService, PeopleService peopleService) {
        this.bookService = bookService;
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String showBooks(@RequestParam(required = false) Integer page,
                            @RequestParam(required = false) Integer books_per_page,
                            @RequestParam(required = false) Boolean sort_by_year,
                            Model model) {
        if (page != null && books_per_page != null && sort_by_year != null && page >= 0 && books_per_page > 0) {
            model.addAttribute("books", bookService.showAllBooks(page, books_per_page, sort_by_year));
        } else if (page != null && books_per_page != null && page >= 0 && books_per_page > 0) {
            model.addAttribute("books", bookService.showAllBooks(page, books_per_page));
        } else if (sort_by_year != null) {
            model.addAttribute("books", bookService.showAllBooks(sort_by_year));
        } else {
            model.addAttribute("books", bookService.showAllBooks());
        }
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
        bookService.addBook(book);
        return "redirect:/book";
    }

    @GetMapping("{id}")
    public String bookInformation(Model model, @PathVariable int id, @ModelAttribute Person person) {
        model.addAttribute("people", peopleService.showAllPeople());
        model.addAttribute("book", bookService.index(id));
        Book book = bookService.index(id);
        if (bookService.getPerson(id) != null) {
            Person person1 = bookService.getPerson(id);
            model.addAttribute("person", person1);
        }
        return "books/book";
    }

    @GetMapping("{id}/edit")
    public String editingMenu(Model model, @PathVariable int id) {
        model.addAttribute("book", bookService.index(id));
        return "books/edit";
    }


    @PatchMapping("{id}")
    public String editBook(@ModelAttribute("book") @Valid Book book,
                           BindingResult bindingResult,
                           @PathVariable int id) {
        if (bindingResult.hasErrors()) {
            return "books/edit";
        }
        bookService.updateBook(id, book);
        return "redirect:/book";
    }

    @DeleteMapping("{id}")
    public String deleteBook(@PathVariable int id) {
        bookService.deleteBook(id);
        return "redirect:/book";
    }


    @PatchMapping("{id}/edit/person")
    public String updateBookOwner(@ModelAttribute("book") Book book,
                                  @PathVariable int id,
                                  @RequestParam("personId") int personId) {
        bookService.updatePerson(personId, id);
        return "redirect:/book";
    }

    @DeleteMapping("{id}/delete/person")
    public String deleteBookFromPerson(@PathVariable int id) {
        bookService.deletePersonBook(id);
        return "redirect:/book";
    }

    @GetMapping("/search")
    public String searchBook(@RequestParam(required = false) String nameOfBook,
                             Model model) {
        if (nameOfBook != null) {
            List<Book> books = bookService.findBooksByName(nameOfBook);
            model.addAttribute("books", books);
        }

        return "books/search";
    }
}
