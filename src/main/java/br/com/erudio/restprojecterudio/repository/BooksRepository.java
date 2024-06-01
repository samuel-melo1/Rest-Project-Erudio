package br.com.erudio.restprojecterudio.repository;

import br.com.erudio.restprojecterudio.domain.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface BooksRepository extends JpaRepository<Books, Long> {
}
