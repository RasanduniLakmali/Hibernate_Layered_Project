package lk.ijse.dao.custom.impl;

import lk.ijse.config.FactoryConfiguration;
import lk.ijse.dao.custom.OrderDAO;
import lk.ijse.entity.Orders;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {

    @Override
    public boolean save(Orders orders, Session session){
        try {
            session.save(orders);
            return true;
        } catch (Exception e) {
            throw e;
        }
    }

   public Object getCurrentId() {
    Session session = FactoryConfiguration.getInstance().getSession();

    try {
        Query query = session.createQuery("select orderId from Orders ORDER BY orderId DESC LIMIT 1");
        Object currentId = query.uniqueResult();
        return currentId;
    } catch (Exception e) {
        return null;
    }
}
    @Override
    public boolean save(Orders entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public List<Orders> getAll() throws SQLException {
        return null;
    }

    @Override
    public boolean update(Orders dto) {
        return false;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }
}
