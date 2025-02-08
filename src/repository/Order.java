package repository;

import java.util.Objects;

public class Order {
    private String orderBy;
    private OrderValue orderValue;

    public enum OrderValue {
        DESC,
        ASC
    }

    public Order(String orderBy, OrderValue orderValue) {
        this.orderBy = orderBy;
        this.orderValue = orderValue;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public OrderValue getOrderValue() {
        return orderValue;
    }

    public void setOrderValue(OrderValue orderValue) {
        this.orderValue = orderValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(orderBy, order.orderBy) && orderValue == order.orderValue;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderBy, orderValue);
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderBy='" + orderBy + '\'' +
                ", orderValue=" + orderValue +
                '}';
    }
}
