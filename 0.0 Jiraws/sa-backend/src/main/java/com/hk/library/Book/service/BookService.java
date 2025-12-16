package com.hk.library.Book.service;

import com.hk.library.Book.model.exception.BookCreationException;
import com.hk.library.Book.persistence.BookRepository;
import com.hk.library.Book.model.BookEntity;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Service;

import java.time.Year;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookEntity createBook(String isbn, String bookName,Integer bookPages,Integer year , String description) throws BookCreationException {

        if(isbn == null || StringUtils.isBlank(isbn)){
            throw new BookCreationException("Le ISBN can't be null or vide!");
        }

        //On verifie l'isbn avec notre methode d'instance
        if(!BookService.isValidIsbn13(isbn)){
            throw new BookCreationException("L'isbn du book doit etre valide");
        }

        if(bookName == null || StringUtils.isBlank(bookName)){
            //return "Le bookName can't be null or vide!";
            throw new BookCreationException("Le bookName can't be null or vide!");
        }

        if(bookPages == null || bookPages <= 0){
            throw new BookCreationException("le bookPage ne peut avoir moins d'une page");
        }

        if(year == null || year > Year.now().getValue()){
            throw new BookCreationException("L'année de parution ne peut être null ou posterieur a l'année actuelle");
        }

        BookEntity existingBook = bookRepository.findByIsbn(isbn);
        //BookEntity existingBook = bookRepository.findByNameAndPages(bookName, bookPages);

        if (existingBook != null) {
            throw new BookCreationException("le livre existe déjà");
        }

        BookEntity newBook = BookEntity.builder()
                .isbn(isbn)
                .name(bookName)
                .pages(bookPages)
                .year(year)
                .description(description)
                .build();

        bookRepository.save(newBook);

        return newBook;
    }


    public static boolean isValidIsbn13(String rawIsbn) {
        if (rawIsbn == null) return false;

        // Supprime tout sauf les chiffres
        String isbn = rawIsbn.replaceAll("[\\s-]+", ""); // \D = tout ce qui n'est pas un chiffre
        System.out.println(isbn);
        if (isbn.length() != 13)
            return false;

        return true;

//        int sum = 0;
//        for (int i = 0; i < 12; i++) {
//            int digit = isbn.charAt(i) - '0';
//            sum += (i % 2 == 0) ? digit : digit * 3;
//        }
//
//        int checkDigit = (10 - (sum % 10)) % 10;
//        return checkDigit == (isbn.charAt(12) - '0');
    }

}
