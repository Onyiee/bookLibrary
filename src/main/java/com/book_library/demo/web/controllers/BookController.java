package com.book_library.demo.web.controllers;

import com.book_library.demo.data.model.Book;
import com.book_library.demo.service.Book.BookService;
import com.book_library.demo.web.exceptions.BookNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@Slf4j
@RequestMapping("")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping("")
    public String index(Model model){
        return "index";
    }

    @GetMapping("book/{id}")
    public String findBookById(@PathVariable("id") String id, Model model)  {
        try {
            Book book = bookService.findBookById(id);
            model.addAttribute("book", book);
            return "foundBook";
        }catch (BookNotFoundException e){
           return "redirect:/";
        }

    }

    @GetMapping("book/all")
    public String search(@RequestParam(name = "bookName") String name, Model model){
        List<Book> books = bookService.search(name);
        model.addAttribute("booklist", books);
        return "books";
    }
}
