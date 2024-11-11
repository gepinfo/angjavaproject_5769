package com.geppetto.angjavaproject.service;

import com.geppetto.angjavaproject.dto.PersonDto;
import org.springframework.data.domain.Page;

public interface PersonService {

    PersonDto  getPersonById(String id);

    PersonDto  createPerson(PersonDto personDto);

    String  deletePerson(String id);

    PersonDto  updatePerson(PersonDto personDto);

    Page<PersonDto>  getAllPerson(int page, int size);

}
