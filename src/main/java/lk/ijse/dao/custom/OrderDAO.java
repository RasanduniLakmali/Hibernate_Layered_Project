package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.Orders;
import org.hibernate.Session;

public interface OrderDAO extends CrudDAO<Orders> {

    public boolean save(Orders orders, Session session);
    public Object getCurrentId();
}
