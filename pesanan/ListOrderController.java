
package pesanan;

import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import produk.Produk;
import resto.LoginManager;
import user.User;
import user.UserModel;

public class ListOrderController {

    @FXML
    private TableColumn<Order, String> aksiColumn;

    @FXML
    private Button confirmButton;

    @FXML
    private AnchorPane dkk;

    @FXML
    private TableColumn<Order, Integer> noColumn;

    @FXML
    private TextField status;

    @FXML
    private TableColumn<Order, String> statusColumn;

    @FXML
    private Label statusLabel;

    @FXML
    private TableView<Order> tablePesanan;

    @FXML
    private TitledPane title;

    @FXML
    private TableColumn<Order, Integer> userColumn;

    private ObservableList<Order> orderList = FXCollections.observableArrayList();
    
    @FXML
     public void initSessionID(final LoginManager loginManager,User user) {
    
           
          noColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        userColumn.setCellValueFactory(new PropertyValueFactory<>("user"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
         aksiColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        
         noColumn.setCellFactory(col -> new TableCell<Order, Integer>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setText(null);
                } else {
                    
                    setText(String.valueOf(getIndex() + 1));
                    
                }
            }
        });
         
        userColumn.setCellFactory(col -> new TableCell<Order, Integer>() {
    @Override
    protected void updateItem(Integer item, boolean empty) {
        super.updateItem(item, empty);
 if (empty || item == null) {
            setText(null); // Clear the text if the item is empty
        } else {
            setText(UserModel.getNama(item));
        }
    }
});

         
         
          
        aksiColumn.setCellFactory(col -> new TableCell<>() {
            final Button deleteButton = new Button("PROSESS");
            final Button delButton = new Button("DELETE");
            final Button detailButton = new Button("LIHAT");
            {
                deleteButton.setOnAction(event -> {
                    Order order = getTableView().getItems().get(getIndex());
                    String stat = order.getStatus();
                    System.out.println(stat);
                    if(stat.equals("WAITING")){
                        
                        orderList.remove(order);
                         
                        order.setStatus("COOKING");
                        OrderModel.updateOrder(order);
                        
                        orderList.add(order);
                    }else if(stat.equals("COOKING")){
                        
                        orderList.remove(order);
                         
                        order.setStatus("DONE");
                        OrderModel.updateOrder(order);
                     
                        orderList.add(order);
                        
                    }
                  
                   
                });
               
                delButton.setOnAction(event -> {
                    Order order = getTableView().getItems().get(getIndex());
                    orderList.remove(order);
                    OrderModel.deleteOrder(order.getId());
                });
                
                detailButton.setOnAction(event -> {
                    Order order = getTableView().getItems().get(getIndex());
                    boolean isOrder = true;
                        try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pesanan/pesanan.fxml"));
                         Stage listOrder = new Stage();
                         listOrder.initModality(Modality.APPLICATION_MODAL);
                         listOrder.setScene(new Scene(loader.load()));

                         PesananController produkController = loader.getController();
                         produkController.isOrder = isOrder;
                         produkController.ordered = order;
                         produkController.initSessionID(loginManager,user);
                         
                         // Show the katalog and wait for it to be closed
                         listOrder.show();

                     } catch (IOException e) {
                         e.printStackTrace();
                     }
                });
                
            }
            
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(new HBox(deleteButton,delButton,detailButton));
                }
            }
         
        });
         
        orderList = OrderModel.getAllOrdersPaid();
        tablePesanan.setItems(orderList);
        
        confirmButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
          closePage();
      }});
         
     }
    
     private void closePage() {
        ((Stage) dkk.getScene().getWindow()).close();
       
    }
}
