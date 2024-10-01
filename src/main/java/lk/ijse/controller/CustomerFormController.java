package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.CustomerBO;
import lk.ijse.dto.CustomerDTO;
import lk.ijse.view.tdm.CustomerTm;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class CustomerFormController {

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colTel;

    @FXML
    private TableView<CustomerTm> tblCustomer;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtTel;

    @FXML
    private AnchorPane rootNode;
    CustomerBO customerBO = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);

    public void initialize(){
        setCellValueFactory();
        loadAllCustomers();
    }

    private void setCellValueFactory(){
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colTel.setCellValueFactory(new PropertyValueFactory<>("tel"));
    }
    private void loadAllCustomers(){
        ObservableList<CustomerTm> obList = FXCollections.observableArrayList();

        try {
            List<CustomerDTO> customerList = customerBO.getAllCustomers();
            for (CustomerDTO customerDTO : customerList){
                CustomerTm customerTm = new CustomerTm(
                        customerDTO.getId(),
                        customerDTO.getName(),
                        customerDTO.getAddress(),
                        customerDTO.getTel()
                );
                obList.add(customerTm);
            }
            tblCustomer.setItems(obList);
        }
        catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
         clearFields();
    }
    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtTel.setText("");

    }
    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String customerId = txtId.getText();

        try {
            boolean isDeleted = customerBO.deleteCustomer(customerId);
            if (isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION,"Customer deleted!").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String customerId= txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String telNo = txtTel.getText();

        CustomerDTO customerDTO = new CustomerDTO(customerId,name,address,telNo);

        try {
            boolean isSaved = customerBO.saveCustomer(customerDTO);
            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION,"Customer saved!").show();
                loadAllCustomers();
            }
        }
        catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String customerId= txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String telNo = txtTel.getText();

        CustomerDTO customerDTO = new CustomerDTO(customerId,name,address,telNo);

        try {
            boolean isUpdated = customerBO.updateCustomer(customerDTO);
            if (isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION,"Customer updated!").show();
                loadAllCustomers();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }
    @FXML
    void txtSearchOnAction(ActionEvent event) {

    }
    @FXML
    void btnHomeOnAction(ActionEvent event) throws IOException {
        AnchorPane node = FXMLLoader.load(this.getClass().getResource("/dashboard_form.fxml"));
        Scene scene =  new Scene(node);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Dashboard Form");
    }



}
