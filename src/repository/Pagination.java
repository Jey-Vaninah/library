package repository;

import java.util.Objects;

public class Pagination {
  private int page;
  private int pageSize;

  public Pagination(int page, int pageSize) {
    this.page = page;
    this.pageSize = pageSize;
  }

  @Override
  public String toString() {
    return "Pagination{" +
        "page=" + page +
        ", pageSize=" + pageSize +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Pagination that = (Pagination) o;
    return page == that.page && pageSize == that.pageSize;
  }

  @Override
  public int hashCode() {
    return Objects.hash(page, pageSize);
  }

  public int getPage() {
    return page;
  }

  public void setPage(int page) {
    this.page = page;
  }

  public int getPageSize() {
    return pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }
}
