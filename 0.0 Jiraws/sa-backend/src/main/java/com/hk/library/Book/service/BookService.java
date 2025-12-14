package com.hk.library.Book.service;

import com.hk.library.Book.model.exception.BookCreationException;
import com.hk.library.Book.persistence.BookRepository;
import com.hk.library.Book.model.BookEntity;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookEntity createBook(String bookName,Integer bookPages) throws BookCreationException {

        if(bookName == null || StringUtils.isBlank(bookName)){
            //return "Le bookName can't be null or vide!";
            throw new BookCreationException("Le bookName can't be null or vide!");
        }
        if(bookPages == null || bookPages <= 0){
            throw new BookCreationException("le bookPage ne peut avoir moins d'une page");
        }

        BookEntity existingBook = bookRepository.findByNameAndPages(bookName, bookPages);

        if (existingBook != null) {
            throw new BookCreationException("le livre existe déjà");
        }

        BookEntity newBook = BookEntity.builder()
                .name(bookName)
                .pages(bookPages)
                .build();

        bookRepository.save(newBook);

        return newBook;
    }
}
