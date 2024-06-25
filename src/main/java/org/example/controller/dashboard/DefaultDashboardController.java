package org.example.controller.dashboard;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.example.dto.LoggedUser;
import org.example.entity.EmployeeEntity;
import java.io.IOException;

public class DefaultDashboardController {
    public Label txtDisplayName;
    public void btnManageOrdersOnAction(ActionEvent actionEvent) {
        try {
            Parent fxmlLoader = new FXMLLoader(getClass().getResource("/view/manage_order_form.fxml")).load();
            Stage stage = new Stage();
            stage.setScene(new Scene(fxmlLoader));
            stage.show();
        } catch (IOException ignored) { }
    }
    public void btnManageInventoryOnAction(ActionEvent actionEvent) {
        try {
            Parent fxmlLoader = new FXMLLoader(getClass().getResource("/view/manage_inventory_form.fxml")).load();
            Stage stage = new Stage();
            stage.setScene(new Scene(fxmlLoader));
            stage.show();
        } catch (IOException ignored) { }
    }
    public void initialize(){
        EmployeeEntity loggedUser = LoggedUser.getInstance().getUser();
        txtDisplayName.setText(loggedUser.getEmpTitle()+". "+loggedUser.getEmpName());
    }
    public void btnBackOnAction(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/login_form.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            Stage currentStage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void btnExitOnAction(ActionEvent actionEvent) {
        Platform.exit();
    }
}
