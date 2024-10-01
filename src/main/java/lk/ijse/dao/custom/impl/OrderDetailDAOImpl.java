package lk.ijse.dao.custom.impl;

import lk.ijse.dao.custom.OrderDetailDAO;
import lk.ijse.entity.OrderDetails;
import org.hibernate.Session;

import java.sql.SQLException;
import java.util.List;

public class OrderDetailDAOImpl implements OrderDetailDAO {
    @Override
    public boolean save(OrderDetails entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public List<OrderDetails> getAll() throws SQLException {
        return null;
    }

    @Override
    public boolean update(OrderDetails dto) {
        return false;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }
    @Override
    public boolean save(List<OrderDetails> orderDetails, Session session){
        try {
            for (OrderDetails orderDetails1 : orderDetails){
                boolean isSaved = save(orderDetails1,session);
            }
            return true;
        } catch (Exception e) {
            throw e;
        }
    }
    @Override
    public boolean save(OrderDetails orderDetails,Session session){
        session.save(orderDetails);
        return true;
    }
}
