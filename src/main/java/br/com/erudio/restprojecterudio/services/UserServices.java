package br.com.erudio.restprojecterudio.services;


import br.com.erudio.restprojecterudio.controllers.PersonController;
import br.com.erudio.restprojecterudio.data.vo.v1.PersonVO;
import br.com.erudio.restprojecterudio.exceptions.ResourceNotFoundException;
import br.com.erudio.restprojecterudio.mapper.DozerMapper;
import br.com.erudio.restprojecterudio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class UserServices implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public UserServices(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private Logger logger = Logger.getLogger(PersonService.class.getName());

    public PersonVO findById(Long id) {

        var entity = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this Id"));

        PersonVO vo = DozerMapper.parseObject(entity, PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
        return vo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Finding one user by name " + username + "!");
        var user = userRepository.findByUserName(username);

        if(user != null){
            return user;
        }

        throw new UsernameNotFoundException("Username " + username + "not found!");
    }

}
