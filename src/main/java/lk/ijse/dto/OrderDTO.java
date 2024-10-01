package lk.ijse.dto;

import lk.ijse.entity.Customer;

import java.util.Date;

public class OrderDTO {
    private String orderId;
    private Date date;
    private Customer customer;

    public OrderDTO() {
    }

    public OrderDTO(String orderId, Date date, Customer customer) {
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
}
