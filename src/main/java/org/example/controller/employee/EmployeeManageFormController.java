package org.example.controller.employee;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.bo.BoFactory;
import org.example.bo.custom.EmployeeBo;
import org.example.dto.Employee;
import org.example.dto.tm.Table01;
import org.example.dto.tm.Table02;
import org.example.entity.EmployeeEntity;
import org.example.util.BoType;
import javafx.scene.Node;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class EmployeeManageFormController implements Initializable {
    public JFXTextField txtEmpId;
    public ChoiceBox cmbEmpTitle;
    public JFXTextField txtEmpName;
    public DatePicker dateDob;
    public JFXTextField txtSalary, txtAddress;
    public JFXTextField txtEmail;
    public JFXTextField txtPassword;
    public TableView tblEmployee1;
    public TableColumn colEmpId;
    public TableColumn colEmpTitle;
    public TableColumn colEmpName;
    public TableColumn colDob;
    public TableView tblEmployee2;
    public TableColumn colEmpIdTbl2;
    public TableColumn colSalary;
    public TableColumn colAddress;
    public TableColumn colRole;
    public TableColumn colEmail;
    public TableColumn colPassword;
    public ChoiceBox cmbRole;


    private EmployeeBo employeeBo = BoFactory.getInstance().getBo(BoType.EMPLOYEE);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colEmpId.setCellValueFactory(new PropertyValueFactory<>("empId"));
        colEmpIdTbl2.setCellValueFactory(new PropertyValueFactory<>("empId"));
        colEmpTitle.setCellValueFactory(new PropertyValueFactory<>("empTitle"));
        colEmpName.setCellValueFactory(new PropertyValueFactory<>("empName"));
        colDob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        loadInitialValues();
        loadTable01();
        loadTable02();


    }

    private void loadTable01() {
        ObservableList<Table01> tbl01 = FXCollections.observableArrayList();
        List<EmployeeEntity> allEmp = employeeBo.getAllEmployees();

        allEmp.forEach(employee -> {
            tbl01.add(
                    new Table01(
                            employee.getId(),
                            employee.getEmpTitle(),
                            employee.getEmpName(),
                            employee.getEmail(),
                            employee.getPassword()
                    )
            );
        });
        tblEmployee1.setItems(tbl01);
    }

    private void loadTable02() {
        ObservableList<Table02> tbl02 = FXCollections.observableArrayList();
        List<EmployeeEntity> allEmp = employeeBo.getAllEmployees();

        allEmp.forEach(employee -> {
            tbl02.add(
                    new Table02(
                            employee.getId(),
                            employee.getSalary(),
                            employee.getAddress(),
                            employee.getRole(),
                            employee.getDob()
                    )
            );
        });
        tblEmployee2.setItems(tbl02);
    }

    private void loadInitialValues() {
        cmbEmpTitle.setValue("Select Title");
        ObservableList list = FXCollections.observableArrayList();
        list.add(new String("Mr"));
        list.add(new String("Mrs"));
        list.add(new String("Ms"));
        cmbEmpTitle.setItems(list);

        cmbRole.setValue("Select Role");
        ObservableList listRole = FXCollections.observableArrayList();
        listRole.add(new String("Admin"));
        listRole.add(new String("Default"));
        cmbRole.setItems(listRole);
    }

    public void btnAddCustomerOnAction(ActionEvent actionEvent) {
        try {
            if (txtEmpId.getText().isEmpty() ||
                    cmbEmpTitle.getValue() == null || cmbEmpTitle.getValue().toString().isEmpty() ||
                    txtEmpName.getText().isEmpty() ||
                    dateDob.getValue() == null ||
                    txtSalary.getText().isEmpty() ||
                    txtAddress.getText().isEmpty() ||
                    cmbRole.getValue() == null || cmbRole.getValue().toString().isEmpty() ||
                    txtEmail.getText().isEmpty() ||
                    txtPassword.getText().isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "Unable to Add. Complete all Fields!").show();
                return;
            }

            Date date = java.sql.Date.valueOf(dateDob.getValue());

            Employee employee = new Employee(
                    txtEmpId.getText(),
                    cmbEmpTitle.getValue().toString(),
                    txtEmpName.getText(),
                    date,
                    Double.parseDouble(txtSalary.getText()),
                    txtAddress.getText(),
                    cmbRole.getValue().toString(),
                    txtEmail.getText(),
                    txtPassword.getText()
            );

            boolean isSaved = employeeBo.saveEmployee(employee);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee Added!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Employee Not Added!").show();
            }

            loadInitialValues();
            loadTable01();
            loadTable02();
            clear();

        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid salary format!").show();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "An unexpected error occurred!").show();
            e.printStackTrace();
        }
    }


    public void btnSearchOnAction(ActionEvent actionEvent) {
        EmployeeEntity employee = employeeBo.findEmployeeById(txtEmpId.getText());
        if (employee==null) {
            new Alert(Alert.AlertType.ERROR, "Employee not found !").show();
        } else {
            txtEmpId.setText(employee.getId());
            cmbEmpTitle.setValue(employee.getEmpTitle());
            txtEmpName.setText(employee.getEmpName());

            java.sql.Date sqlDate = (java.sql.Date) employee.getDob();
            dateDob.setValue(sqlDate.toLocalDate());
            txtSalary.setText(String.valueOf(employee.getSalary()));
            txtAddress.setText(employee.getAddress());
            cmbRole.setValue(employee.getRole());
            txtEmail.setText(employee.getEmail());
            txtPassword.setText(employee.getPassword());
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        boolean result = employeeBo.deleteById(txtEmpId.getText());
        if (result) {
            new Alert(Alert.AlertType.CONFIRMATION, "Employee deleted successfully !").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Employee not found or could not be deleted !").show();
        }
        loadInitialValues();
        loadTable01();
        loadTable02();
        clear();
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        try {
            java.sql.Date dob = java.sql.Date.valueOf(dateDob.getValue());

            if (txtEmpId.getText().isEmpty() ||
                    cmbEmpTitle.getValue() == null || cmbEmpTitle.getValue().toString().isEmpty() ||
                    txtEmpName.getText().isEmpty() ||
                    dob == null ||
                    txtSalary.getText().isEmpty() ||
                    txtAddress.getText().isEmpty() ||
                    cmbRole.getValue() == null || cmbRole.getValue().toString().isEmpty() ||
                    txtEmail.getText().isEmpty() ||
                    txtPassword.getText().isEmpty()) {

                new Alert(Alert.AlertType.ERROR, "Please fill out all fields before updating.").show();
                return;
            }

            EmployeeEntity employee = new EmployeeEntity(
                    txtEmpId.getText(),
                    cmbEmpTitle.getValue().toString(),
                    txtEmpName.getText(),
                    dob,
                    Double.parseDouble(txtSalary.getText()),
                    txtAddress.getText(),
                    cmbRole.getValue().toString(),
                    txtEmail.getText(),
                    txtPassword.getText()
            );

            boolean result = employeeBo.updateEmployee(employee);
            if (result) {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee updated successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Employee not found or could not be updated!").show();
            }

        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid salary format!").show();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "An unexpected error occurred!").show();
            e.printStackTrace();
        }

        loadInitialValues();
        loadTable01();
        loadTable02();
        clear();
    }

    public void clear() {
        txtEmpId.setText(null);
        txtEmpName.setText(null);
        dateDob.setValue(null);
        txtSalary.setText(null);
        txtAddress.setText(null);
        txtEmail.setText(null);
        txtPassword.setText(null);
    }

    public void btnBackOnAction(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
