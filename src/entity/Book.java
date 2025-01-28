package entity;

import java.time.LocalDate;
import java.util.Objects;

public class Book {
    private String id;
    private String name;
    private Author author;
    private int page_numbers;
    private Topic topic;
    private LocalDate release_date;

    public Book(String id, String name, Author author, int page_numbers, Topic topic, LocalDate release_date) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.page_numbers = page_numbers;
        this.topic = topic;
        this.release_date = release_date;
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

    public int getPage_numbers() {
        return page_numbers;
    }

    public void setPage_numbers(int page_numbers) {
        this.page_numbers = page_numbers;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public LocalDate getReleaseDate() {
        return release_date;
    }

    public void setReleaseDate(LocalDate release_date) {
        this.release_date = release_date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return page_numbers == book.page_numbers && Objects.equals(id, book.id) && Objects.equals(name, book.name) && Objects.equals(author, book.author) && topic == book.topic && Objects.equals(release_date, book.release_date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, author, page_numbers, topic, release_date);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", author=" + author +
                ", page_numbers=" + page_numbers +
                ", topic=" + topic +
                ", release_date=" + release_date +
                '}';
    }
}