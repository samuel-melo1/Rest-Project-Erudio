package br.com.erudio.restprojecterudio.repository;

import br.com.erudio.restprojecterudio.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
}
