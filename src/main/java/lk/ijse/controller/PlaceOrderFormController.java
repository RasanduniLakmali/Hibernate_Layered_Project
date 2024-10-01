package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.CustomerBO;
import lk.ijse.bo.custom.ItemBO;
import lk.ijse.bo.custom.PlaceOrderBO;
import lk.ijse.dto.CustomerDTO;
import lk.ijse.dto.ItemDTO;
import lk.ijse.dto.OrderDTO;
import lk.ijse.dto.OrderDetailDTO;
import lk.ijse.entity.Customer;
import lk.ijse.entity.OrderDetails;
import lk.ijse.view.tdm.OrderDetailsTm;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class PlaceOrderFormController {

    @FXML
    private ComboBox<String> cmbCustomerId;

    @FXML
    private ComboBox<String> cmbItemId;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colRemove;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private TableView<OrderDetailsTm> tblOrderDetails;

    @FXML
    private TextField txtCusName;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtOrderId;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtQtyOnHand;

    @FXML
    private TextField txtTotal;

    @FXML
    private TextField txtUnitPrice;

    @FXML
    private AnchorPane rootNode;

    private ObservableList<OrderDetailsTm> obList = FXCollections.observableArrayList();
    PlaceOrderBO placeOrderBO = (PlaceOrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PLACE_ORDER);
    CustomerBO customerBO = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);

    ItemBO itemBO = (ItemBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ITEM);
    public void initialize(){
        getItemCodes();
        getCustomerIds();
        setCellValueFactory();
        getCurrentOrderId();
        setDate();
    }
    private void setCellValueFactory(){
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("ItemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("Description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("UnitPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("Qty"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("Total"));

    }
    private void setDate() {
        LocalDate now = LocalDate.now();
        txtDate.setText(String.valueOf(now));
    }
    @FXML
    void btnAddToCartOnAction(ActionEvent event) {
         String itemCode = cmbItemId.getValue();
         String description = txtDescription.getText();
         int qty = Integer.parseInt(txtQty.getText());
         double unitPrice = Double.parseDouble(txtUnitPrice.getText());
         double total = unitPrice * qty;


        for (int i = 0; i < tblOrderDetails.getItems().size(); i++) {
            if (itemCode.equals(colItemCode.getCellData(i))) {

                OrderDetailsTm tm = obList.get(i);
                qty += tm.getQty();
                total = qty * unitPrice;

                tm.setQty(qty);
                tm.setTotal(total);

                tblOrderDetails.refresh();

                calculateNetAmount();
                return;
            }

        }
        OrderDetailsTm tm = new OrderDetailsTm(itemCode,description,qty,unitPrice,total);
        obList.add(tm);

        tblOrderDetails.setItems(obList);
        calculateNetAmount();
    }
    private void calculateNetAmount(){
        int netAmount = 0;
        for (int i = 0; i < tblOrderDetails.getItems().size(); i++) {
            netAmount += (double) colTotal.getCellData(i);
        }
        txtTotal.setText(String.valueOf(netAmount));

    }
    private void getCurrentOrderId(){
        try {
            Object currentId = placeOrderBO.currentId();
            String nextId = generateNextOrderID(currentId);
            txtOrderId.setText(nextId);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private String generateNextOrderID(Object currentId) {

        currentId = (String) placeOrderBO.currentId();
        System.out.println(currentId);
        if (currentId != null) {
            int curId = Integer.parseInt(((String) currentId).replace("OID-", "") )+ 1;
            String nextId = String.format("OID-%03d", curId);
            return nextId;
        }else{
            return "OID-001";
        }
    }
    private void getItemCodes(){
        try {
            List<ItemDTO> itemDTOS = placeOrderBO.getAllItems();

            for (ItemDTO itemDTO : itemDTOS){
                cmbItemId.getItems().add(itemDTO.getItemCode());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void getCustomerIds(){
        try {
            List<CustomerDTO> customerDTOS = placeOrderBO.getAllCustomers();

            for (CustomerDTO customerDTO : customerDTOS){
                cmbCustomerId.getItems().add(customerDTO.getId());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {

        String orderId = txtOrderId.getText();
        Date date = java.sql.Date.valueOf(LocalDate.now());

        CustomerDTO customerDTO = customerBO.searchCustomer(cmbCustomerId.getValue());

        Customer customer = new Customer();
        customer.setId(cmbCustomerId.getValue());
        customer.setName(customerDTO.getName());
        customer.setAddress(customerDTO.getAddress());
        customer.setTel(customerDTO.getTel());

        OrderDTO orderDTO = new OrderDTO(orderId,date,customer);

        List<OrderDetailDTO> dtoList = new ArrayList<>();
        for (int i =0;i < tblOrderDetails.getItems().size();i++){
            OrderDetailsTm tm = obList.get(i);

            OrderDetailDTO orderDetailDTO = new OrderDetailDTO(
                    orderId,
                    tm.getItemCode(),
                    tm.getQty(),
                    tm.getUnitPrice()
            );
            dtoList.add(orderDetailDTO);
        }

       placeOrderBO.placeOrder(orderDTO,dtoList);

//        if (isPlaced){
//            new Alert(Alert.AlertType.CONFIRMATION,"Order placed successfully!").show();
//        }else {
//            new Alert(Alert.AlertType.ERROR,"Order not placed!").show();
//        }
    }
//    public boolean saveOrder(OrderDTO orderDTO,List<OrderDetailDTO> dtoList) throws SQLException, ClassNotFoundException {
//        return placeOrderBO.placeOrder(orderDTO,dtoList);
//    }

    @FXML
    void btnHomeOnAction(ActionEvent event) throws IOException {
        AnchorPane node = FXMLLoader.load(this.getClass().getResource("/dashboard_form.fxml"));
        Scene scene =  new Scene(node);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Dashboard Form");
    }

    @FXML
    void cmbIdSearchOnAction(ActionEvent event) throws SQLException {
        String cusId = cmbCustomerId.getSelectionModel().getSelectedItem();
        CustomerDTO customer = customerBO.searchCustomer(cusId);
        txtCusName.setText(customer.getName());

    }

    @FXML
    void cmbItemIdSearchOnAction(ActionEvent event) throws SQLException {
       String itemId = cmbItemId.getSelectionModel().getSelectedItem();
        ItemDTO itemDTO = itemBO.searchItem(itemId);
        txtDescription.setText(itemDTO.getDescription());
        txtUnitPrice.setText(String.valueOf(itemDTO.getUnitPrice()));
        txtQtyOnHand.setText(String.valueOf(itemDTO.getQty()));


    }

}
