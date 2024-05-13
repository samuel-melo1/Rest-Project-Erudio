package br.com.erudio.restprojecterudio.mapper.custom;

import br.com.erudio.restprojecterudio.data.vo.v2.PersonVO2;
import br.com.erudio.restprojecterudio.domain.Person;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PersonMapper {

    public PersonVO2 convertEntityToVo(Person person){
        PersonVO2 vo = new PersonVO2();
        vo.setId(person.getId());
        vo.setAddress(person.getAddress());
        vo.setBirthDate(new Date());
        vo.setGender(person.getGender());
        vo.setFirstName(person.getFirstName());
        vo.setLastName(person.getLastName());
        return vo;
    }

    public Person convertVoToEntity(PersonVO2 person){
        Person entity = new Person();
        entity.setId(person.getId());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        return entity;
    }
}
