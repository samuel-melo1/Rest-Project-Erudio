package br.com.erudio.restprojecterudio.mocks;

import br.com.erudio.restprojecterudio.data.vo.v1.BooksVO;
import br.com.erudio.restprojecterudio.domain.Books;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MockBook {

    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
    public Books mockEntity() throws ParseException {
        return mockEntity(0);
    }
    public BooksVO mockVO() throws ParseException {
        return mockVO(0);
    }
    public List<Books> mockEntityList() throws ParseException {
        List<Books> books = new ArrayList<Books>();
        for (int i = 0; i < 14; i++) {
            books.add(mockEntity(i));
        }
        return books;
    }
    public List<BooksVO> mockVOList() throws ParseException {
        List<BooksVO> booksVO = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            booksVO.add(mockVO(i));
        }
        return booksVO;
    }
    public Books mockEntity(Integer number) throws ParseException {
        Date date = formatter.parse("26-09-1989");
        Books books = new Books();
        books.setAuthor("Author Test" + number);
        books.setTitle("Title Test" + number);
        books.setPrice(29.4);
        books.setId(number.longValue());
        books.setLaunch_date(date);
        return books;
    }
    public BooksVO mockVO(Integer number) throws ParseException {
        Date date = formatter.parse("26-09-1989");
        BooksVO booksVO = new BooksVO();
        booksVO.setAuthor("Author Test" + number);
        booksVO.setTitle("Title Test" + number);
        booksVO.setPrice(29.4);
        booksVO.setKey(number.longValue());
        booksVO.setLaunch_date(date);
        return booksVO;
    }
}
