package test.utils;

import entity.Book;
import java.time.LocalDate;

import static entity.Topic.COMEDY;
import static entity.Topic.ROMANCE;
import static test.utils.AuthorTestDataUtils.*;

public class BookTestDataUtils {
  public static Book histoiresRomantiques() {
    return new Book(
        "B001",
        "Histoires Romantiques",
        marieCurie(),
        320,
        ROMANCE,
        LocalDate.of(2001, 9, 25)
    );
  }

  public static Book leGrandRire() {
    return new Book(
        "B002",
        "Le Grand Rire",
        jeanDupont(),
        180,
        COMEDY,
        LocalDate.of(1995, 6, 12)
    );
  }

  public static Book rireEtVie() {
    return new Book(
        "B003",
        "Rire et Vie",
        albertCamus(),
        150,
        COMEDY,
        LocalDate.of(1945, 3, 18)
    );
  }
}
