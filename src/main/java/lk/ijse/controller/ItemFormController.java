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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.ItemBO;
import lk.ijse.dto.ItemDTO;
import lk.ijse.view.tdm.ItemTm;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ItemFormController {

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colItCode;

    @FXML
    private TableColumn<?, ?> colPrice;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableView<ItemTm> tblItem;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtUnitPrice;

    @FXML
    private AnchorPane rootNode;

    ItemBO itemBO = (ItemBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ITEM);

    public void initialize(){
        setCellValueFactory();
        loadAllItems();
    }
    private void setCellValueFactory(){
        colItCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
    }
    public void loadAllItems(){
        ObservableList<ItemTm> obList = FXCollections.observableArrayList();

        try {
            List<ItemDTO> itemList = itemBO.getAllItems();
            for (ItemDTO itemDTO : itemList){
                ItemTm itemTm = new ItemTm(
                        itemDTO.getItemCode(),
                        itemDTO.getDescription(),
                        itemDTO.getUnitPrice(),
                        itemDTO.getQty()
                );
                obList.add(itemTm);
            }
            tblItem.setItems(obList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    @FXML
    void btnClearOnAction(ActionEvent event) {
         clearFields();
    }
    private void clearFields() {
        txtId.setText("");
        txtDescription.setText("");
        txtUnitPrice.setText("");
        txtQty.setText("");

    }
    @FXML
    void btnDeleteOnAction(ActionEvent event) {
       int iCode = Integer.parseInt(txtId.getText());

        try {
            boolean isDeleted = itemBO.deleteItem(iCode);
            if (isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION,"item deleted!").show();
                tblItem.refresh();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
       int iCode = Integer.parseInt(txtId.getText());
       String description = txtDescription.getText();
       double unitPrice = Double.parseDouble(txtUnitPrice.getText());
       int qty = Integer.parseInt(txtQty.getText());

       ItemDTO itemDTO = new ItemDTO(iCode,description,unitPrice,qty);

        try {
            boolean isSaved = itemBO.saveItem(itemDTO);
            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION,"Item saved!").show();
                loadAllItems();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        int iCode = Integer.parseInt(txtId.getText());
        String description = txtDescription.getText();
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        int qty = Integer.parseInt(txtQty.getText());

        ItemDTO itemDTO = new ItemDTO(iCode,description,unitPrice,qty);

        try {
            boolean isUpdated = itemBO.updateItem(itemDTO);
            if (isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION,"Item updated!").show();
                tblItem.refresh();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
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

