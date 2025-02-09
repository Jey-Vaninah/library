package test.utils;

import entity.Author;

import java.time.LocalDate;

import static entity.Gender.FEMALE;
import static entity.Gender.MALE;

public class AuthorTestDataUtils {
  public static Author jeanDupont(){
    return new Author(
      "A001",
      "Jean Dupont",
      MALE,
      LocalDate.parse("2003-07-05")
    );
  }

  public static Author marieCurie(){
    return new Author(
      "A002",
      "Marie Curie",
      FEMALE,
      LocalDate.parse("2007-06-04")
    );
  }

  public static Author albertCamus(){
    return new Author(
      "A003",
      "Albert Camus",
      MALE,
      LocalDate.parse("2002-07-05")
    );
  }
}
