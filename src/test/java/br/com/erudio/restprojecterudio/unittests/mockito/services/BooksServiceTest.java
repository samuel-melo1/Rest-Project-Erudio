package br.com.erudio.restprojecterudio.unittests.mockito.services;

import br.com.erudio.restprojecterudio.data.vo.v1.BooksVO;
import br.com.erudio.restprojecterudio.data.vo.v1.PersonVO;
import br.com.erudio.restprojecterudio.domain.Books;
import br.com.erudio.restprojecterudio.domain.Person;
import br.com.erudio.restprojecterudio.exceptions.RequireObjectIsNullException;
import br.com.erudio.restprojecterudio.mocks.MockBook;
import br.com.erudio.restprojecterudio.mocks.MockPerson;
import br.com.erudio.restprojecterudio.repository.BooksRepository;
import br.com.erudio.restprojecterudio.services.BooksService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class BooksServiceTest {

    MockBook input;
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
    @InjectMocks
    private BooksService service;
    @Mock
    private BooksRepository repository;
    @BeforeEach
    void setUpMocks() {
        input = new MockBook();
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void findAll() throws ParseException {
        Date date = formatter.parse("26-09-1989");
        List<Books> list = input.mockEntityList();

        when(repository.findAll()).thenReturn(list);
        var books  = service.findAll();
        assertNotNull(books);
        assertEquals(14,books.size());

        var booksOne = books.get(1);

        assertNotNull(booksOne);
        assertNotNull(booksOne.getKey());
        assertNotNull(booksOne.getLinks());
        assertTrue(booksOne.toString().contains("links: [</api/books/v1/1>;rel=\"self\"]"));
        assertEquals("Author Test1", booksOne.getAuthor());
        assertEquals("Title Test1", booksOne.getTitle());
        assertEquals(29.4, booksOne.getPrice());
        assertEquals(date, booksOne.getLaunch_date());

        var booksFour = books.get(4);

        assertNotNull(booksFour);
        assertNotNull(booksFour.getKey());
        assertNotNull(booksFour.getLinks());
        assertTrue(booksFour.toString().contains("links: [</api/books/v1/4>;rel=\"self\"]"));
        assertEquals("Author Test4", booksFour.getAuthor());
        assertEquals("Title Test4", booksFour.getTitle());
        assertEquals(29.4, booksFour.getPrice());
        assertEquals(date, booksFour.getLaunch_date());

        var booksSeven = books.get(7);

        assertNotNull(booksSeven);
        assertNotNull(booksSeven.getKey());
        assertNotNull(booksSeven.getLinks());
        assertTrue(booksSeven.toString().contains("links: [</api/books/v1/7>;rel=\"self\"]"));
        assertEquals("Author Test7", booksSeven.getAuthor());
        assertEquals("Title Test7", booksSeven.getTitle());
        assertEquals(29.4, booksSeven.getPrice());
        assertEquals(date, booksSeven.getLaunch_date());
    }
    @Test
    void create() throws ParseException {
        Date date = formatter.parse("26-09-1989");

        Books entity = input.mockEntity(1);
        Books persisted = entity;
        persisted.setId(1L);

        BooksVO vo = input.mockVO(1);
        vo.setKey(1L);

        when(repository.save(entity)).thenReturn(persisted);
        var result = service.create(vo);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</api/books/v1/1>;rel=\"self\"]"));
        assertEquals("Author Test1", result.getAuthor());
        assertEquals("Title Test1", result.getTitle());
        assertEquals(29.4, result.getPrice());
        assertEquals(date, result.getLaunch_date());
    }

    @Test
    void createWithNullBook() {
        Exception exception = assertThrows(RequireObjectIsNullException.class, () -> {
            service.create(null);
        });
        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void update() throws ParseException {
        Date date = formatter.parse("26-09-1989");
        Books entity = input.mockEntity(1);
        entity.setId(1L);

        Books persisted = entity;

        BooksVO vo = input.mockVO(1);
        vo.setKey(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(persisted);

        var result = service.update(vo);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</api/books/v1/1>;rel=\"self\"]"));
        assertEquals("Author Test1", result.getAuthor());
        assertEquals("Title Test1", result.getTitle());
        assertEquals(29.4, result.getPrice());
        assertEquals(date, result.getLaunch_date());
    }

    @Test
    void updateWithNullBook() {
        Exception exception = assertThrows(RequireObjectIsNullException.class, () -> {
            service.update(null);
        });
        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
    @Test
    void delete() throws ParseException {
        Books entity = input.mockEntity(1);
        entity.setId(1L);
        when(repository.findById(entity.getId())).thenReturn(Optional.of(entity));

        service.delete(1L);
    }

    @Test
    void findById() throws ParseException {
        Date date = formatter.parse("26-09-1989");
        Books entity = input.mockEntity(1);
        entity.setId(1L);
        when(repository.findById(entity.getId())).thenReturn(Optional.of(entity));

        var result = service.findById(1L);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</api/books/v1/1>;rel=\"self\"]"));
        assertEquals("Author Test1", result.getAuthor());
        assertEquals("Title Test1", result.getTitle());
        assertEquals(29.4, result.getPrice());
        assertEquals(date, result.getLaunch_date());
    }
}