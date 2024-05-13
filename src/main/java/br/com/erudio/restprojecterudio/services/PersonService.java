package br.com.erudio.restprojecterudio.services;

import br.com.erudio.restprojecterudio.data.vo.v1.PersonVO;
import br.com.erudio.restprojecterudio.data.vo.v2.PersonVO2;
import br.com.erudio.restprojecterudio.domain.Person;
import br.com.erudio.restprojecterudio.exceptions.ResourceNotFoundException;
import br.com.erudio.restprojecterudio.mapper.DozerMapper;
import br.com.erudio.restprojecterudio.mapper.custom.PersonMapper;
import br.com.erudio.restprojecterudio.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.logging.Logger;

@Service
public class PersonService {
    private Logger logger = Logger.getLogger(PersonService.class.getName());

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private PersonMapper mapper;

    public List<PersonVO> findAll(){
        logger.info("Finding all people!");
        return DozerMapper.parseListObjects(personRepository.findAll(), PersonVO.class);
    }
    public PersonVO create(PersonVO person){
        logger.info("creating one person!");

            var entity =  DozerMapper.parseObject(person, Person.class);
            return DozerMapper.parseObject(personRepository.save(entity), PersonVO.class);
    }
    public PersonVO2 createV2(PersonVO2 person){
        logger.info("creating one person with v2!");

        var entity =  mapper.convertVoToEntity(person);
        return mapper.convertEntityToVo(personRepository.save(entity));
    }
    public PersonVO update(PersonVO person){
        logger.info("updating one person!");

       var entity = personRepository.findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this Id"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());
        return DozerMapper.parseObject(personRepository.save(entity), PersonVO.class);
    }

    public void delete(Long id){
        logger.info("deleting one person!");

        var entity = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this Id"));

        personRepository.delete(entity);
    }
    public PersonVO findById(Long id){
        logger.info("Finding one person!");
        var entity = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this Id"));

        return DozerMapper.parseObject(entity, PersonVO.class);
    }


}
