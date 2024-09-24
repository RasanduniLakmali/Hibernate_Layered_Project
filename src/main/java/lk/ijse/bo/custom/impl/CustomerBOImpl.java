package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.CustomerBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.CustomerDAO;
import lk.ijse.dto.CustomerDTO;
import lk.ijse.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerBOImpl  implements CustomerBO {

    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    @Override
    public boolean saveCustomer(CustomerDTO customerDTO) throws SQLException,ClassNotFoundException {
        return customerDAO.save(new Customer(customerDTO.getId(),customerDTO.getName(),customerDTO.getAddress(),customerDTO.getTel()));
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = customerDAO.getAll();
        List<CustomerDTO> cusList = new ArrayList<>();

        for (Customer customer : customers){
            CustomerDTO customerDTO = new CustomerDTO(customer.getId(),customer.getName(),customer.getAddress(),customer.getTel());
            cusList.add(customerDTO);
        }
        return cusList;
    }
    @Override
    public boolean updateCustomer(CustomerDTO customerDTO){
        return customerDAO.update(new Customer(customerDTO.getId(),customerDTO.getName(),customerDTO.getAddress(),customerDTO.getTel()));
    }

    @Override
    public boolean deleteCustomer(int customerId) {
        return customerDAO.delete(customerId);
    }
}
