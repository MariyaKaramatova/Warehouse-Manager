package model;

import java.time.LocalDateTime;

public class Moving {
    private int id;
    private LocalDateTime date;
    private int commodity_id;
    private int quantity;
    private Type type;

    public Moving(int id, LocalDateTime date, int commodity_id, int quantity, Type type) {
        this.id = id;
        this.date = date;
        this.commodity_id = commodity_id;
        this.quantity = quantity;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public int getCommodity_id() {
        return commodity_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public Type getType() {
        return type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setCommodity_id(int commodity_id) {
        this.commodity_id = commodity_id;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
