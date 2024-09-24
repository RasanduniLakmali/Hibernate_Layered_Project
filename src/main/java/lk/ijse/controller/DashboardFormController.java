package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardFormController {

    @FXML
    private AnchorPane rootNode;

    @FXML
    void btnCustomerOnAction(ActionEvent event) throws IOException {
        AnchorPane node = FXMLLoader.load(this.getClass().getResource("/customer_form.fxml"));
        Scene scene = new Scene(node);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Customer Form");
    }

    @FXML
    void btnItemOnAction(ActionEvent event) throws IOException {
        AnchorPane node = FXMLLoader.load(this.getClass().getResource("/item_form.fxml"));
        Scene scene = new Scene(node);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Item Form");
    }

    @FXML
    void btnOrdersOnAction(ActionEvent event) throws IOException {
        AnchorPane node = FXMLLoader.load(this.getClass().getResource("/order_form.fxml"));
        Scene scene = new Scene(node);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Order Form");
    }

}

