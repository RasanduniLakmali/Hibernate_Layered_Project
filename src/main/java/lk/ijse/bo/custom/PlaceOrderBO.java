package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.CustomerDTO;
import lk.ijse.dto.ItemDTO;
import lk.ijse.dto.OrderDTO;
import lk.ijse.dto.OrderDetailDTO;
import lk.ijse.entity.OrderDetails;

import java.sql.SQLException;
import java.util.List;

public interface PlaceOrderBO extends SuperBO {

    public List<ItemDTO> getAllItems() throws SQLException;
    public List<CustomerDTO> getAllCustomers() throws SQLException;

    public void placeOrder(OrderDTO orderDTO, List<OrderDetailDTO> orderDetailsList) throws SQLException, ClassNotFoundException;
    public Object currentId();

}
