package lk.ijse.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class Orders {

    @Id
    private String orderId;

    private Date date;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "orders")
    List<OrderDetails> orderDetailsList;

    public Orders() {
    }

    public Orders(String orderId, Date date, Customer customer, List<OrderDetails> orderDetailsList) {
        this.orderId = orderId;
        this.date = date;
        this.customer = customer;
        this.orderDetailsList = orderDetailsList;
    }

    public Orders(String orderId, Date date, Customer customer) {
        this.orderId = orderId;
        this.date = date;
        this.customer = customer;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<OrderDetails> getOrderDetailsList() {
        return orderDetailsList;
    }

    public void setOrderDetailsList(List<OrderDetails> orderDetailsList) {
        this.orderDetailsList = orderDetailsList;
    }
}
