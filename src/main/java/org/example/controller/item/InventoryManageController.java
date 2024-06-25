package org.example.controller.item;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.bo.BoFactory;
import org.example.bo.custom.ItemBo;
import org.example.dto.Employee;
import org.example.dto.Item;
import org.example.dto.tm.ItemsInventory;
import org.example.entity.EmployeeEntity;
import org.example.entity.ItemEntity;
import org.example.util.BoType;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class InventoryManageController implements Initializable {
    public JFXTextField txtItemCode;
    public ChoiceBox cmbSize;
    public JFXTextField txtDesc;
    public JFXTextField txtStock;
    public JFXTextField txtPrice;
    public TableView tblItem;
    public TableColumn colItemCode;
    public TableColumn colDesc;
    public TableColumn colSize;
    public TableColumn colPrice;
    public TableColumn colStock;
    private ItemBo itemBo = BoFactory.getInstance().getBo(BoType.ITEM);
    public void btnAddItemOnAction(ActionEvent actionEvent) {
        try {
            if (txtItemCode.getText().isEmpty() ||
                    txtDesc.getText().isEmpty() ||
                    cmbSize.getValue() == null || cmbSize.getValue().toString().isEmpty() ||
                    txtPrice.getText().isEmpty() ||
                    txtStock.getText().isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "Please fill out all fields before adding the item.").show();
                return;
            }

            double price = Double.parseDouble(txtPrice.getText());
            int stock = Integer.parseInt(txtStock.getText());

            Item item = new Item(
                    txtItemCode.getText(),
                    txtDesc.getText(),
                    cmbSize.getValue().toString(),
                    price,
                    stock
            );

            boolean b = itemBo.saveItem(item);
            if (b) {
                new Alert(Alert.AlertType.CONFIRMATION, "Item Added!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Item Not Added!").show();
            }

            loadInitialValues();
            loadTable();
            clear();

        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid price or stock format!").show();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "An unexpected error occurred!").show();
            e.printStackTrace();
        }
    }
    public void btnSearchOnAction(ActionEvent actionEvent) {
        ItemEntity item = itemBo.findItemById(txtItemCode.getText());
        if (item==null) {
            new Alert(Alert.AlertType.ERROR, "Item not found !").show();
        } else {
            txtItemCode.setText(item.getId());
            txtDesc.setText(item.getDescription());
            cmbSize.setValue(item.getSize());
            txtPrice.setText(String.valueOf(item.getUnitPrice()));
            txtStock.setText(String.valueOf(item.getQty()));
        }
    }
    public void btnDeleteOnAction(ActionEvent actionEvent) {
        boolean result = itemBo.deleteById(txtItemCode.getText());
        if (result) {
            new Alert(Alert.AlertType.CONFIRMATION, "Item deleted successfully !").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Item not found or could not be deleted !").show();
        }
        loadInitialValues();
        loadTable();
        clear();
    }
    public void btnUpdateOnAction(ActionEvent actionEvent) {
        try {

            if (txtItemCode.getText().isEmpty() ||
                    txtDesc.getText().isEmpty() ||
                    cmbSize.getValue() == null || cmbSize.getValue().toString().isEmpty() ||
                    txtPrice.getText().isEmpty() ||
                    txtStock.getText().isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "Please fill out all fields before updating the item.").show();
                return;
            }

            double price = Double.parseDouble(txtPrice.getText());
            int stock = Integer.parseInt(txtStock.getText());

            ItemEntity item = new ItemEntity(
                    txtItemCode.getText(),
                    txtDesc.getText(),
                    cmbSize.getValue().toString(),
                    price,
                    stock
            );

            boolean result = itemBo.updateItem(item);
            if (result) {
                new Alert(Alert.AlertType.CONFIRMATION, "Item updated successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Item not found or could not be updated!").show();
            }

        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid price or stock format!").show();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "An unexpected error occurred!").show();
            e.printStackTrace();
        }
        loadInitialValues();
        loadTable();
        clear();
    }
    public void btnBackOnAction(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        colSize.setCellValueFactory(new PropertyValueFactory<>("size"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colStock.setCellValueFactory(new PropertyValueFactory<>("qty"));

        loadInitialValues();
        loadTable();
    }
    private void loadInitialValues() {
        cmbSize.setValue("Select Size");
        ObservableList list = FXCollections.observableArrayList();
        list.add(new String("XXXS"));
        list.add(new String("XXS"));
        list.add(new String("XS"));
        list.add(new String("S"));
        list.add(new String("M"));
        list.add(new String("L"));
        list.add(new String("XL"));
        list.add(new String("XXL"));
        list.add(new String("XXXL"));
        cmbSize.setItems(list);
    }
    private void loadTable() {
        ObservableList<ItemsInventory> tbl = FXCollections.observableArrayList();
        List<ItemEntity> allItem = itemBo.getAllItems();

        allItem.forEach(item -> {
            tbl.add(
                    new ItemsInventory(
                            item.getId(),
                            item.getDescription(),
                            item.getSize(),
                            item.getUnitPrice(),
                            item.getQty()
                    )
            );
        });
        tblItem.setItems(tbl);
    }
    public void clear() {
        txtItemCode.setText(null);
        txtDesc.setText(null);
        txtPrice.setText(null);
        txtStock.setText(null);
    }
}
