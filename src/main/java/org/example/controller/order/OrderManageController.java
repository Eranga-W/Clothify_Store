package org.example.controller.order;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.bo.BoFactory;
import org.example.bo.custom.ItemBo;
import org.example.bo.custom.OrderBo;
import org.example.dto.LoggedUser;
import org.example.dto.Order;
import org.example.dto.OrderDetail;
import org.example.dto.tm.CartTbl;
import org.example.entity.EmployeeEntity;
import org.example.entity.ItemEntity;
import org.example.entity.OrderEntity;
import org.example.util.BoType;

import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class OrderManageController implements Initializable {

    public ComboBox cmbItemCode;
    public Label lblTime;
    public Label lblDate;
    public Label lblOrderId;
    public Label lblName;
    public Label lblDesc;
    public Label lblSize;
    public Label lblUnitPrice;
    public Label lblQty;
    public TableView tblCart;
    public TableColumn colItemCode;
    public TableColumn colDesc;
    public TableColumn colQty;
    public TableColumn colUnitPrice;
    public TableColumn colTotal;
    public TextField txtReqQty;
    public Label lblNetTotal;
    public Label lblEmpId;

    private ItemBo itemBo = BoFactory.getInstance().getBo(BoType.ITEM);
    private OrderBo orderBo = BoFactory.getInstance().getBo(BoType.ORDER);
    ObservableList<CartTbl> cartList = FXCollections.observableArrayList();
    public void btnAddToCartOnAction(ActionEvent actionEvent) {
        if (txtReqQty.getText().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please add Quantity !").show();
            return;
        }
        String itemCode = (String) cmbItemCode.getValue();
        String desc = lblDesc.getText();
        Integer reqQty = Integer.parseInt(txtReqQty.getText());
        Double unitPrice = Double.valueOf(lblUnitPrice.getText());
        Double total = reqQty * unitPrice;
        CartTbl cartTbl = new CartTbl(itemCode, desc, reqQty, unitPrice, total);

        int stockQty = Integer.parseInt(lblQty.getText());
        if (stockQty < reqQty) {
            new Alert(Alert.AlertType.WARNING, "Insufficient Stock !").show();
            return;
        }

        ItemEntity itemById = itemBo.findItemById(itemCode);
        Integer remainingStock = itemById.getQty() - reqQty;
        itemById.setQty(remainingStock);

        cartList.add(cartTbl);
        tblCart.setItems(cartList);
        calNetTotal();
        clear();
    }
    public void txtAddtoCartOnAction(ActionEvent actionEvent) {btnAddToCartOnAction(actionEvent);}
    public void calNetTotal() {
        double tot = 0;
        for (CartTbl cartObj : cartList) {
            tot += cartObj.getTotal();
        }
        lblNetTotal.setText(String.valueOf(tot) + "/=");
    }
    public void btnClearOnAction(ActionEvent actionEvent) {
        if (!cartList.isEmpty()) {
            cartList.remove(cartList.size() - 1);
            new Alert(Alert.AlertType.CONFIRMATION, "Last Item Cleared !").show();
        }else {
            new Alert(Alert.AlertType.ERROR, "Cart is Empty !").show();
        }
    }
    public void btnPlaceOrderOnAction(ActionEvent actionEvent) {
        try {
            if (cartList.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Cart is empty. Add items to the cart before placing an order !").show();
                return;
            }

            String orderId = lblOrderId.getText();
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date orderDate = format.parse(lblDate.getText());
            String empID = lblEmpId.getText();
            Double netTotal = 0.0;
            List<OrderDetail> orderDetailList = new ArrayList<>();
            for (CartTbl cartTbl : cartList) {
                String itemCode = cartTbl.getItemCode();
                Double uPrice = cartTbl.getUnitPrice();
                Integer qty = cartTbl.getQty();
                Double total = uPrice*qty;
                netTotal+=total;
                orderDetailList.add(new OrderDetail(null,orderId, itemCode,uPrice, qty, total));
            }

            Order order = new Order(orderId, orderDate, empID, netTotal, orderDetailList);
            Boolean isOrderPlace = orderBo.saveOrder(order);
            if (isOrderPlace) {
                generateOrderId();
                clear();
                lblNetTotal.setText(null);
                cartList=null;
                tblCart.setItems(cartList);
                new Alert(Alert.AlertType.INFORMATION, "Order Placed Successfully !").show();
            }else{
                new Alert(Alert.AlertType.ERROR, "Order not Placed !").show();
            }

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDateAndTime();
        generateOrderId();
        loadItemCodes();
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("desc"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        cmbItemCode.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(cmbItemCode.getValue()!=null){
                setItemDataForLbl((String) newValue);
            }
        });

        EmployeeEntity loggedUser = LoggedUser.getInstance().getUser();
        lblEmpId.setText(loggedUser.getId());
        lblName.setText(loggedUser.getEmpName());
    }
    private void loadDateAndTime() {
        //Date
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));


        //Time
        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime time = LocalTime.now();
            lblTime.setText(
                    time.getHour() + " : " + time.getMinute() + " : " + time.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    public void generateOrderId() {
        Long count = orderBo.countAllOrders();
        if (count == 0) {
            lblOrderId.setText("OR0001");
            return;
        }
        do {
            String assumedOrderId = String.format("OR%04d", (count+1));
            OrderEntity order = orderBo.findOrderById(assumedOrderId);
            if (order == null) {
                lblOrderId.setText(assumedOrderId);
                return;
            }else{
                count++;
            }
        }while (true);
    }
    private void loadItemCodes() {
        List<ItemEntity> allItems = itemBo.getAllItems();
        ObservableList<String> itemCodes = FXCollections.observableArrayList();

        allItems.forEach(item -> {
            itemCodes.add(item.getId());
        });

        cmbItemCode.setItems(itemCodes);
    }
    private void setItemDataForLbl(String ItemCode) {
        ItemEntity item = itemBo.findItemById(ItemCode);
        lblDesc.setText(item.getDescription());
        lblSize.setText(item.getSize());
        lblUnitPrice.setText(String.valueOf(item.getUnitPrice()));
        lblQty.setText(String.valueOf(item.getQty()));
    }
    private void clear() {
        cmbItemCode.setValue(null);
        lblDesc.setText(null);
        lblSize.setText(null);
        lblUnitPrice.setText(null);
        lblQty.setText(null);
        txtReqQty.setText(null);
    }
    public void btnBackOnAction(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
