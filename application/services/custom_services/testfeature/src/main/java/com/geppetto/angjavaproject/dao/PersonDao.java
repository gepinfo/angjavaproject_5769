package com.geppetto.angjavaproject.dao;

import java.util.Optional;
import com.geppetto.angjavaproject.model.Person;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

public interface PersonDao {

    Optional<Person> getPersonById(String id);

    Person createPerson(Person person);

    void deletePerson(String id);

    Optional<Person> updatePerson(String id);

    Page<Person> getAllPerson(Pageable pageable);

}

