package lk.ijse.dao.custom.impl;

import lk.ijse.config.FactoryConfiguration;
import lk.ijse.dao.custom.CustomerDAO;
import lk.ijse.entity.Customer;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public boolean save(Customer customer) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Object isSaved = session.save(customer);

        if (isSaved != null) {
            transaction.commit();
            session.close();
            return true;
        }
        return false;
    }

    @Override
    public List<Customer> getAll() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("from Customer");
        List<Customer> customers = query.list();
        transaction.commit();
        session.close();
        return customers;

    }

    @Override
    public boolean update(Customer customer) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.update(customer);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(String id) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("delete from Customer where id = ?1");
        query.setParameter(1, id);
        boolean isDeleted = query.executeUpdate() > 0;

        if (isDeleted) {
            transaction.commit();
            session.close();
            return true;
        }
        return false;
    }

    @Override
    public Customer search(String customerId) throws SQLException {

        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from Customer where id =?1");
        query.setParameter(1, customerId);
        Customer customer = (Customer) query.uniqueResult();
        transaction.commit();
        return customer;
    }

}
