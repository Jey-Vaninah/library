package entity;

import java.time.LocalDate;
import java.util.Objects;

public class Book {
    private String id;
    private String name;
    private Author author;
    private Integer pageNumbers;
    private Topic topic;
    private LocalDate releaseDate;

    public Book(String id, String name, Author author, int pageNumbers, Topic topic, LocalDate releaseDate) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.pageNumbers = pageNumbers;
        this.topic = topic;
        this.releaseDate = releaseDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Integer getPageNumbers() {
        return pageNumbers;
    }

    public void setPageNumbers(Integer pageNumbers) {
        this.pageNumbers = pageNumbers;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id) && Objects.equals(name, book.name) && Objects.equals(author, book.author) && Objects.equals(pageNumbers, book.pageNumbers) && topic == book.topic && Objects.equals(releaseDate, book.releaseDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, author, pageNumbers, topic, releaseDate);
    }

    @Override
    public String toString() {
        return "Book{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", author=" + author +
            ", pageNumbers=" + pageNumbers +
            ", topic=" + topic +
            ", releaseDate=" + releaseDate +
            '}';
    }
}