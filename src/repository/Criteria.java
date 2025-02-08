package repository;

public class Criteria {
    private String column;
    private Object value;

    public Object getValue() {
        return value;
    }

    public String getColumn() {
        return column;
    }

    public Criteria(String column, Object value) {
        this.column = column;
        this.value = value;
    }
}


