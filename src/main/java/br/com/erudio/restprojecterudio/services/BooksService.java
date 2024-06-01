package br.com.erudio.restprojecterudio.services;

import br.com.erudio.restprojecterudio.controllers.BooksController;
import br.com.erudio.restprojecterudio.controllers.PersonController;
import br.com.erudio.restprojecterudio.data.vo.v1.BooksVO;
import br.com.erudio.restprojecterudio.domain.Books;
import br.com.erudio.restprojecterudio.exceptions.RequireObjectIsNullException;
import br.com.erudio.restprojecterudio.exceptions.ResourceNotFoundException;
import br.com.erudio.restprojecterudio.mapper.DozerMapper;
import br.com.erudio.restprojecterudio.repository.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.logging.Logger;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BooksService {
    private Logger logger = Logger.getLogger(BooksController.class.getName());

    @Autowired
    private BooksRepository repository;

    public List<BooksVO> findAll() {
        logger.info("Finding all people!");
        var books = DozerMapper.parseListObjects(repository.findAll(), BooksVO.class);
        books.stream().forEach(p -> p.add(linkTo(methodOn(BooksController.class).findById(p.getKey())).withSelfRel()));
        return books;
    }

    public BooksVO create(BooksVO books) {
        logger.info("creating one book!");

        if (books == null) throw new RequireObjectIsNullException();

        var entity = DozerMapper.parseObject(books, Books.class);
        BooksVO vo = DozerMapper.parseObject(repository.save(entity), BooksVO.class);
        vo.add(linkTo(methodOn(BooksController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }
    public BooksVO update(BooksVO books) {
        if (books == null) throw new RequireObjectIsNullException();

        logger.info("updating one books!");
        var entity = repository.findById(books.getKey())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this Id"));

        entity.setAuthor(books.getAuthor());
        entity.setLaunch_date(books.getLaunch_date());
        entity.setPrice(books.getPrice());
        entity.setTitle(books.getTitle());
        BooksVO vo = DozerMapper.parseObject(repository.save(entity), BooksVO.class);
        vo.add(linkTo(methodOn(BooksController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public void delete(Long id) {
        logger.info("deleting one book!");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this Id"));

        repository.delete(entity);
    }

    public BooksVO findById(Long id) {
        logger.info("Finding one book!");
        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this Id"));

        BooksVO vo = DozerMapper.parseObject(entity, BooksVO.class);
        vo.add(linkTo(methodOn(BooksController.class).findById(id)).withSelfRel());
        return vo;
    }
}
