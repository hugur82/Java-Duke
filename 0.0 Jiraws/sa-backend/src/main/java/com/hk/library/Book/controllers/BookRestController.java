package com.hk.library.Book.controllers;

import com.hk.library.Book.dto.BookDTO;
import com.hk.library.Book.service.BookService;
import lombok.extern.slf4j.Slf4j;
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
    public String post(@RequestBody BookDTO.PostInput input){

        log.info(input.getBookName());
        log.info(String.valueOf(input.getBookPages()));

        String response = bookService.createBook(input.getBookName(), input.getBookPages());
        return response;
    }
}
