package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.dto.CustomerDTO;
import lk.ijse.entity.Customer;

import java.sql.SQLException;
import java.util.List;

public interface CustomerDAO extends CrudDAO<Customer> {
    public boolean save(Customer customer) throws SQLException;
    public List<Customer> getAll();
    public  Customer search(String customerId) throws SQLException;
}
