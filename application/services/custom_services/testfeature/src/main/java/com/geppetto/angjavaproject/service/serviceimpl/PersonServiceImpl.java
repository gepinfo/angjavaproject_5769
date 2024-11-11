package com.geppetto.angjavaproject.service.serviceimpl;

import com.geppetto.angjavaproject.dao.PersonDao;
import com.geppetto.angjavaproject.dto.PersonDto;
import com.geppetto.angjavaproject.exception.EntityNotFoundException;
import com.geppetto.angjavaproject.model.Person;
import com.geppetto.angjavaproject.service.PersonService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;


/**
* Implementation of the {@link PersonService} interface.
* Provides services related to Person, including CRUD operations and file uploads/downloads.
*/

@Service
@Slf4j
public class PersonServiceImpl implements PersonService {


    /**
     * Constructs a {@code PersonServiceImpl} with the specified DAO and MongoTemplate.
     *
     * @param personDao The DAO for accessing the data.
     * @param mongoTemplate The MongoTemplate for interacting with MongoDB.
     */
  private final PersonDao personDao;
  private final MongoTemplate mongoTemplate;

  public PersonServiceImpl(PersonDao  personDao, MongoTemplate mongoTemplate) {
    this. personDao =  personDao;
    this.mongoTemplate = mongoTemplate;
  }
    
    /**
     * Retrieves person by its ID.
     *
     * @param id The ID of the person to retrieve. Must not be {@code null}.
     * @return The person data transfer object associated with the specified ID.
     * @throws EntityNotFoundException If no person with the specified ID is found.
     */
  @Override
  public PersonDto  getPersonById(String id) {
    log.info("Enter into getPersonById method");
    return personDao.getPersonById(id)
    .map(person -> {
      PersonDto personDto = new PersonDto();
      BeanUtils.copyProperties(person, personDto);
      log.info("Exit from getPersonById method");
      return personDto;
    })
        .orElseThrow(() -> new EntityNotFoundException("Data not found for ID: " + id));
  }
    
    /**
     * Creates new person.
     *
     * @param personDto The {@link PersonDto} to be created.
     * @return The created {@link PersonDto}.
     */
  @Override
  public PersonDto  createPerson(PersonDto personDto) {
    log.info("Enter into createPerson method");
    Person person = new Person();
  BeanUtils.copyProperties(personDto, person);
  Person createdPerson= personDao.createPerson(person);
  BeanUtils.copyProperties(createdPerson, personDto);
  log.info("Exit from createPerson method");
  return personDto;
  }
    
    /**
     * Deletes person by ID.
     *
     * @param id The ID of the person to delete.
     * @return A message indicating the result of the deletion.
     * @throws EntityNotFoundException If no person with the specified ID is found.
     */
  @Override
  public String  deletePerson(String id) {
    log.info("Enter into deletePerson method");
    return personDao.getPersonById(id)
     .map(person -> {
     personDao.deletePerson(id);
  log.info("Exit from deletePerson method");
  return "Data Deleted Successfully";
  })
  .orElseThrow(() -> new EntityNotFoundException("No entry found with ID: " + id + ". Unable to delete."));

  }
    
    /**
     * Updates existing person.
     *
     * @param personDto The {@link PersonDto} containing updated information.
     * @return The updated {@link PersonDto}.
     * @throws EntityNotFoundException If no person with the specified ID is found.
     */
  @Override
  public PersonDto  updatePerson(PersonDto personDto) {
    log.info("Enter into updatePerson method");
    return personDao.getPersonById(personDto.getId())
    .map(existingPerson -> {
      BeanUtils.copyProperties(personDto, existingPerson);
      personDao.createPerson(existingPerson);
      log.info("Exit from updatePerson method");
      return personDto;
  })
  .orElseThrow(() -> new EntityNotFoundException("Data not found for update with ID: " + personDto.getId()));
  }
    
    /**
     * Retrieves all person.
     *
     * @return A list of {@link PersonDto} representing all person.
     */
  @Override
  public Page<PersonDto>  getAllPerson(int page, int size) {
    log.info("Enter into getAllPerson method");
    Pageable pageable = (Pageable) PageRequest.of(page, size);
    Page<Person> personPage =personDao.getAllPerson(pageable);
    Page<PersonDto>personDtoPage = personPage.map(person -> {
    PersonDto personDto = PersonDto.builder().build();
    BeanUtils.copyProperties(person, personDto);
    return personDto;
    });
    log.info("Exit from getAllpersonmethod");
    return personDtoPage;
  }
    

}
