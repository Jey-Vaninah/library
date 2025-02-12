package repository;

import java.util.List;

public interface Repository<T> {
  List<T> findAll(Pagination pagination, Order order);
  T findById(String id);
  T deleteById(String id);
  T create(T id);
  T update(T id);
  T crupdate(T id);
  List<T> findByCriteria(List<Criteria> criteria, Order order, Pagination pagination);
}
