package MyCompany.sa_backend;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<BookEntity,Long> {

    public BookEntity findByNameAndPages(String name, Integer pages);
}
