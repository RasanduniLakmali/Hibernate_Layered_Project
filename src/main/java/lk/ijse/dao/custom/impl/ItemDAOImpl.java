package lk.ijse.dao.custom.impl;

import lk.ijse.bo.custom.ItemBO;
import lk.ijse.config.FactoryConfiguration;
import lk.ijse.dao.custom.ItemDAO;
import lk.ijse.entity.Item;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

public class ItemDAOImpl implements ItemDAO {

    @Override
    public boolean save(Item item) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Object isSaved = session.save(item);
        if (isSaved != null){
            transaction.commit();
            session.close();
            return true;
        }
        return false;
    }

    @Override
    public List<Item> getAll() throws SQLException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("from Item ");
        List<Item> items = query.list();
        transaction.commit();
        session.close();
        return items;
    }

    @Override
    public boolean update(Item item) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.update(item);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(int itemCode) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("delete from Item where itemCode = ?1");
        query.setParameter(1,itemCode);
        boolean isDeleted = query.executeUpdate() > 0;

        if (isDeleted){
            transaction.commit();
            session.close();
            return true;
        }
        return false;
    }
}
