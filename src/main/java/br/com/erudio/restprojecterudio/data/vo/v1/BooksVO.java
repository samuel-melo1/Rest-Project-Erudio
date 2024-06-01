package br.com.erudio.restprojecterudio.data.vo.v1;


import com.github.dozermapper.core.Mapping;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class BooksVO extends RepresentationModel<BooksVO> implements Serializable  {

    @Mapping("id")
    private Long key;
    private String author;
    private Date launch_date;
    private Double price;
    private String title;
    public BooksVO() {

    }

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getLaunch_date() {
        return launch_date;
    }

    public void setLaunch_date(Date launch_date) {
        this.launch_date = launch_date;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BooksVO booksVO = (BooksVO) o;
        return Objects.equals(getKey(), booksVO.getKey()) && Objects.equals(getAuthor(), booksVO.getAuthor()) && Objects.equals(getLaunch_date(), booksVO.getLaunch_date()) && Objects.equals(getPrice(), booksVO.getPrice()) && Objects.equals(getTitle(), booksVO.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getKey(), getAuthor(), getLaunch_date(), getPrice(), getTitle());
    }
}
