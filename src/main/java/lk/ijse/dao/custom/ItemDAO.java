package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.Item;
import org.hibernate.Session;

import java.sql.SQLException;

public interface ItemDAO extends CrudDAO<Item> {
    public Item search(String itemCode) throws SQLException;
    public boolean updateQty(Item item, double qty,double unitPrice ,Session session) throws SQLException, ClassNotFoundException;
}
