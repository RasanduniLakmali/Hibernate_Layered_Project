package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.CustomerDTO;

import java.sql.SQLException;
import java.util.List;

public interface CustomerBO  extends SuperBO {
    public boolean saveCustomer(CustomerDTO customerDTO) throws SQLException,ClassNotFoundException;
    public List<CustomerDTO> getAllCustomers();
    public boolean updateCustomer(CustomerDTO customerDTO);
    public boolean deleteCustomer(int customerId);
}
