package br.com.erudio.restprojecterudio.services;

import br.com.erudio.restprojecterudio.controllers.PersonController;
import br.com.erudio.restprojecterudio.data.vo.v1.PersonVO;
import br.com.erudio.restprojecterudio.data.vo.v2.PersonVO2;
import br.com.erudio.restprojecterudio.domain.Person;
import br.com.erudio.restprojecterudio.exceptions.RequireObjectIsNullException;
import br.com.erudio.restprojecterudio.exceptions.ResourceNotFoundException;
import br.com.erudio.restprojecterudio.mapper.DozerMapper;
import br.com.erudio.restprojecterudio.mapper.custom.PersonMapper;
import br.com.erudio.restprojecterudio.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class PersonService {
    private Logger logger = Logger.getLogger(PersonService.class.getName());

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private PersonMapper mapper;

    public List<PersonVO> findAll() {
        logger.info("Finding all people!");
        var persons = DozerMapper.parseListObjects(personRepository.findAll(), PersonVO.class);
        persons.stream().forEach(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));
        return persons;
    }

    public PersonVO create(PersonVO person) {
        logger.info("creating one person!");

        if (person == null) throw new RequireObjectIsNullException();

        var entity = DozerMapper.parseObject(person, Person.class);
        PersonVO vo = DozerMapper.parseObject(personRepository.save(entity), PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public PersonVO2 createV2(PersonVO2 person) {
        logger.info("creating one person with v2!");

        var entity = mapper.convertVoToEntity(person);
        return mapper.convertEntityToVo(personRepository.save(entity));

    }

    public PersonVO update(PersonVO person) {
        if (person == null) throw new RequireObjectIsNullException();

        logger.info("updating one person!");
        var entity = personRepository.findById(person.getKey())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this Id"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());
        PersonVO vo = DozerMapper.parseObject(personRepository.save(entity), PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public void delete(Long id) {
        logger.info("deleting one person!");

        var entity = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this Id"));

        personRepository.delete(entity);
    }

    public PersonVO findById(Long id) {
        logger.info("Finding one person!");
        var entity = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this Id"));

        PersonVO vo = DozerMapper.parseObject(entity, PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
        return vo;
    }


}
