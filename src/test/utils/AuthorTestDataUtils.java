package test.utils;

import entity.Author;

import static entity.Gender.FEMALE;
import static entity.Gender.MALE;

public class AuthorTestDataUtils {
  public static Author jeanDupont(){
    return new Author(
      "A001",
      "Jean Dupont",
      MALE
    );
  }

  public static Author marieCurie(){
    return new Author(
      "A002",
      "Marie Curie",
      FEMALE
    );
  }

  public static Author albertCamus(){
    return new Author(
      "A003",
      "Albert Camus",
      MALE
    );
  }
}
