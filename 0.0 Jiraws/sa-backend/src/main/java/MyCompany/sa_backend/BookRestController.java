package MyCompany.sa_backend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class BookRestController {
    private final BookRepository bookRepository;

    public BookRestController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/book")
    public String get(@RequestParam String bookName, @RequestParam Integer bookPages)
    {

        log.info(bookName);
        log.info(String.valueOf(bookPages));

        BookEntity existingBook = bookRepository.findByNameAndPages(bookName, bookPages);



        if (existingBook == null) {
            log.info("Aucun livre trouvé pour name={} pages={}", bookName, bookPages);
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
