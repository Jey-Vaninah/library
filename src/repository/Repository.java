package repository;

import java.util.List;

public interface Repository<T> {
  List<T> findAll(Pagination pagination);
  T findById(String id);
  T deleteById(String id);
  T create(T id);
  T update(T id);
  T crupdate(T id);
}
