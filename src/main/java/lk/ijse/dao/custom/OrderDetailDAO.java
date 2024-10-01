package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.OrderDetails;
import org.hibernate.Session;

import java.util.List;

public interface OrderDetailDAO extends CrudDAO<OrderDetails> {

    public boolean save(List<OrderDetails> orderDetails, Session session);
    public boolean save(OrderDetails orderDetails,Session session);
}
