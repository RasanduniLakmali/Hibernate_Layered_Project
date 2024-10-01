package lk.ijse.bo.custom.impl;

import javafx.scene.control.Alert;
import lk.ijse.bo.custom.PlaceOrderBO;
import lk.ijse.config.FactoryConfiguration;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.CustomerDAO;
import lk.ijse.dao.custom.ItemDAO;
import lk.ijse.dao.custom.OrderDAO;
import lk.ijse.dao.custom.OrderDetailDAO;
import lk.ijse.dto.CustomerDTO;
import lk.ijse.dto.ItemDTO;
import lk.ijse.dto.OrderDTO;
import lk.ijse.dto.OrderDetailDTO;
import lk.ijse.entity.Customer;
import lk.ijse.entity.Item;
import lk.ijse.entity.OrderDetails;
import lk.ijse.entity.Orders;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlaceOrderBOImpl implements PlaceOrderBO {

    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);

    OrderDetailDAO orderDetailDAO = (OrderDetailDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER_DETAIL);

    @Override
    public List<ItemDTO> getAllItems() throws SQLException {
        List<Item> items = itemDAO.getAll();
        List<ItemDTO> itemDTOS = new ArrayList<>();

        for (Item item : items){
            ItemDTO itemDTO = new ItemDTO(item.getItemCode(),item.getDescription(),item.getUnitPrice(),item.getQty());
            itemDTOS.add(itemDTO);
        }
        return itemDTOS;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() throws SQLException {
        List<Customer> customers = customerDAO.getAll();
        List<CustomerDTO> customerDTOS = new ArrayList<>();

        for (Customer customer : customers){
            CustomerDTO customerDTO = new CustomerDTO(customer.getId(),customer.getName(),customer.getAddress(),customer.getTel());
            customerDTOS.add(customerDTO);
        }
        return customerDTOS;
    }
    @Override
    public void placeOrder(OrderDTO orderDTO, List<OrderDetailDTO> orderDetailsList) throws SQLException, ClassNotFoundException {

        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        boolean isUpdated = false;

        try {
            Orders orders = new Orders(orderDTO.getOrderId(),orderDTO.getDate(),orderDTO.getCustomer());
            boolean isSaved = orderDAO.save(orders,session);


            if (isSaved){
                List<OrderDetails> orderDetails = new ArrayList<>();

                for (OrderDetailDTO orderDetailDTO : orderDetailsList){
                    Item item = new Item();
                    item.setItemCode(orderDetailDTO.getItemCode());
                    System.out.println(item);
                    isUpdated = itemDAO.updateQty(item,orderDetailDTO.getQty(),orderDetailDTO.getUnitPrice(),session);
                    orderDetails.add(new OrderDetails(orders,item,orderDetailDTO.getQty(),orderDetailDTO.getUnitPrice()));
                }

                if (isUpdated){
                    orderDetailDAO.save(orderDetails,session);
                    transaction.commit();
                    new Alert(Alert.AlertType.CONFIRMATION,"Transaction completed!").show();

                }
            }

        } catch (Exception e){
            transaction.rollback();
            throw e;

        }finally {
            session.close();
        }
    }
   @Override
    public Object currentId() {

        return orderDAO.getCurrentId();
    }

}
