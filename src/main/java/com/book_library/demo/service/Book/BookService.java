package com.book_library.demo.service.Book;

import com.book_library.demo.data.model.Book;
import com.book_library.demo.web.exceptions.BookNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookService {
    Book findBookById(String id) throws BookNotFoundException;
    List<Book> search(String searchSentence);
}
