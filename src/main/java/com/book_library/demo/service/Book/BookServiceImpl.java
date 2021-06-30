package com.book_library.demo.service.Book;

import com.book_library.demo.data.model.*;
import com.book_library.demo.web.exceptions.BookNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BookServiceImpl implements BookService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Book findBookById(String id) throws BookNotFoundException {
        String URI = "https://www.googleapis.com/books/v1/volumes/" + id;
        ResponseEntity<SearchResults> apiResponseResponseEntity =
                restTemplate.getForEntity(URI, SearchResults.class);
        SearchResults searchResults = apiResponseResponseEntity.getBody();
        if (searchResults == null) {
            throw new BookNotFoundException("No book with that ID found. Search for a valid book");
        }
        Book book = convert(searchResults);
        return book;
    }


    @Override
    public List<Book> search(String searchSentence) {
        searchSentence = searchSentence.replaceAll(" ", "");
        final String URI = "https://www.googleapis.com/books/v1/volumes?q=" + searchSentence;
        ResponseEntity<ApiResponse> apiResponseResponseEntity = restTemplate.getForEntity(URI, ApiResponse.class);
        ApiResponse apiResponse = apiResponseResponseEntity.getBody();
        System.out.println(apiResponse);
        assert apiResponse != null;
        List<Book> books = apiResponse.getItems().stream().map(
                searchResults -> convert(searchResults)
        ).collect(Collectors.toList());
        List<Book> filteredBooks = books.stream().filter(book -> book.getTitle() != null).collect(Collectors.toList());
        return filteredBooks;
    }

    private Book convert(SearchResults searchResults) {

        try {
            Book book = new Book();
            String id = searchResults.getId();
            VolumeInfo volumeInfo = searchResults.getVolumeInfo();
            String title = volumeInfo.getTitle();
            String publisher = volumeInfo.getPublisher();
            List<String> author = volumeInfo.getAuthors();
            String previewLink = volumeInfo.getPreviewLink();
            String description = volumeInfo.getDescription();
            ImageLinks imageLinks = volumeInfo.getImageLinks();
            String smallImage = imageLinks.getSmallThumbnail();
            String image = imageLinks.getThumbnail();
            book.setId(id);
            book.setDescription(description);
            book.setImage(image);
            book.setPreviewLink(previewLink);
            book.setPublisher(publisher);
            book.setSmallImage(smallImage);
            book.setTitle(title);
            book.setAuthor(author);
            return book;
        } catch (Exception e) {
            Book emptyBook = new Book();
            return emptyBook;
        }


    }
}
