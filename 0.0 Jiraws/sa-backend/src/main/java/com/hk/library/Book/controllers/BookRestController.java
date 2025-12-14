package com.hk.library.Book.controllers;

import com.hk.library.Book.dto.BookDTO;
import com.hk.library.Book.model.BookEntity;
import com.hk.library.Book.model.exception.BookCreationException;
import com.hk.library.Book.service.BookService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/book")
public class BookRestController {

    private final BookService bookService;

    public BookRestController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public String get(@RequestParam String bookName, @RequestParam Integer bookPages)
    {
//        log.info(bookName);
//        log.info(String.valueOf(bookPages));
//
//        String response = bookService.createBook(bookName, bookPages);
//        return response;
        return "ok get";
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDTO.PostOutput post(@Valid @RequestBody BookDTO.PostInput input) throws BookCreationException {

        log.info(input.getBookName());
        log.info(String.valueOf(input.getBookPages()));

        BookEntity newBook = bookService.createBook(input.getBookName(), input.getBookPages());

        return BookDTO.PostOutput
                .builder()
                .id(newBook.getId())
                .bookName(newBook.getName())
                .bookPages(newBook.getPages())
                .build();
    }
}
