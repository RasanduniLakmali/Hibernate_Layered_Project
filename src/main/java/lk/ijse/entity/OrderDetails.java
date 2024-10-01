package lk.ijse.entity;

import jakarta.persistence.*;
import lk.ijse.bo.custom.ItemBO;

import java.math.BigDecimal;

@Entity
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "oId")
    private Orders orders;

    @ManyToOne
    @JoinColumn(name = "iCode")
    private Item item;

    private int qty;

    private double unitPrice;

    public OrderDetails() {
    }



    public OrderDetails(Orders orders, Item item, int qty, double unitPrice) {
        this.orders = orders;
        this.item = item;
        this.qty = qty;
        this.unitPrice = unitPrice;
    }



    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
}
