package com.hk.library.Book.service;

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

    public String createBook(String bookName,Integer bookPages){

        if(bookName == null || StringUtils.isBlank(bookName)){
            return "Le bookName can't be null or vide!";
        }

        if(bookPages == null || bookPages <= 10){
            return "le bookPage ne peut avoir moins de 10 pages";
        }

        BookEntity existingBook = bookRepository.findByNameAndPages(bookName, bookPages);

        if (existingBook == null) {
            BookEntity newBook = BookEntity.builder()
                    .name(bookName)
                    .pages(bookPages)
                    .build();

            bookRepository.save(newBook);
            return "Le livre a bien été créer";
        } else {
            return "le livre existe déjà";

        }
    }

}
