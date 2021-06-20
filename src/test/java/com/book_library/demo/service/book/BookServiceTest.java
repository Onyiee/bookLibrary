package com.book_library.demo.service.book;

import com.book_library.demo.data.model.Book;
import com.book_library.demo.service.Book.BookServiceImpl;
import com.book_library.demo.web.exceptions.BookNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookServiceTest {
    @Autowired
    BookServiceImpl bookService;
    Book book;

    @BeforeEach
    void setUp() {
        book = new Book();
    }

    @Test
    void findBookById() throws BookNotFoundException {
     book = bookService.findBookById("s1gVAAAAYAAJ");
    assertThat(book.getAuthor().get(0)).isEqualTo("Jane Austen");
    assertThat(book.getTitle()).isEqualTo("Pride and Prejudice");
    assertThat(book.getPublisher()).isEqualTo("C. Scribner's sons");
    assertThat(book.getPreviewLink()).isEqualTo("http://books.google.com/books?id=s1gVAAAAYAAJ&hl=&source=gbs_api");

    }


    @Test
    void search() {
        List<Book> bookList = bookService.search("quilting");
        assertThat(bookList).isNotNull();
        assertThat(bookList).isNotEmpty();
    }
}