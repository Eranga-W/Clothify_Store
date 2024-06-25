package org.example.controller.login;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.example.bo.BoFactory;
import org.example.bo.custom.EmployeeBo;
import org.example.dto.LoggedUser;
import org.example.entity.EmployeeEntity;
import org.example.util.BoType;

import java.io.IOException;

public class LoginController {
    public JFXTextField txtEmail;
    public JFXPasswordField txtPassword;


    private EmployeeBo employeeBo = BoFactory.getInstance().getBo(BoType.EMPLOYEE);
    public void btnLoginOnAction(ActionEvent actionEvent) {
        EmployeeEntity employee = employeeBo.findEmployeeByEmail(txtEmail.getText());
        if (employee == null || !employee.getPassword().equals(txtPassword.getText()) ) {
            new Alert(Alert.AlertType.WARNING, "Login Failed. Try Again !").show();
            return;
        }
        LoggedUser.getInstance().setUser(employee);
        if (employee.getRole().equals("Admin")) {
            try {
                Parent fxmlLoader = new FXMLLoader(getClass().getResource("/view/admin_dashboard.fxml")).load();
                Stage stage = new Stage();
                stage.setScene(new Scene(fxmlLoader));
                stage.show();

                Stage loginStage = (Stage) txtEmail.getScene().getWindow();
                loginStage.close();

            } catch (IOException ignored) { }
        } else {
            try {
                Parent fxmlLoader = new FXMLLoader(getClass().getResource("/view/default_dashboard.fxml")).load();
                Stage stage = new Stage();
                stage.setScene(new Scene(fxmlLoader));
                stage.show();

                Stage loginStage = (Stage) txtEmail.getScene().getWindow();
                loginStage.close();

            } catch (IOException ignored) { }
        }
    }
}
