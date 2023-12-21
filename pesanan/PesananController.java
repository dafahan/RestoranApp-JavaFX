
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

public class PesananController {
    public boolean isOrder = false;
    public Order ordered;
    @FXML
    private Button addButton;

    @FXML
    private TableColumn<Produk, String> aksiColumn;

    @FXML
    private Button confirmButton;

    @FXML
    private AnchorPane dkk;

    @FXML
    private TableColumn<Produk, Integer> hargaColumn;

    @FXML
    private TableColumn<Produk, Integer> noColumn;

    @FXML
    private TableColumn<Produk, String> produkColumn;

    @FXML
    private TextField status;

    @FXML
    private Label statusLabel;

    @FXML
    private TextField subTotal;

    @FXML
    private TableView<Produk> tablePesanan;

    @FXML
    private TitledPane title;
    private ObservableList<Produk> produkList= FXCollections.observableArrayList();
    
    @FXML
     public void initSessionID(final LoginManager loginManager,User user) {
            if(isOrder){
               
                statusLabel.setVisible(true);
                status.setVisible(true);
                status.setText(ordered.getStatus());
                addButton.setVisible(false);
                
                if(!"PAY".equals(ordered.getStatus()))confirmButton.setText("CANCEL");
                if("DONE".equals(ordered.getStatus()))confirmButton.setText("PESAN LAGI");
                if(!"WAITING".equals(ordered.getStatus())&&!"PAY".equals(ordered.getStatus())&&!"DONE".equals(ordered.getStatus()))confirmButton.setDisable(true);
                produkList = OrderModel.getAllProduk(ordered);
                tablePesanan.setItems(produkList);
                 if(user.getRole().equals("admin")) confirmButton.setVisible(false);
            }
         addButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
            try {
                    
                        
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pesanan/addProduk.fxml"));
                         Stage order = new Stage();
                         order.initModality(Modality.APPLICATION_MODAL);
                         order.setScene(new Scene(loader.load()));

                         AddProdukController orderController = loader.getController();
                         produkList = orderController.initSessionID(loginManager,user,produkList);

                         order.showAndWait();
                         order.close();
                        
                         tablePesanan.setItems(produkList);
                         

                     } catch (IOException e) {
                         e.printStackTrace();
                     }
          
                    }
      
      
                });
         
                
         
          noColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        produkColumn.setCellValueFactory(new PropertyValueFactory<>("nama"));
        hargaColumn.setCellValueFactory(new PropertyValueFactory<>("harga"));
         aksiColumn.setCellValueFactory(new PropertyValueFactory<>("nama"));
        
         noColumn.setCellFactory(col -> new TableCell<Produk, Integer>() {
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
         
         
          hargaColumn.setCellFactory(col -> new TableCell<Produk, Integer>() {
        @Override
        protected void updateItem(Integer item, boolean empty) {
            super.updateItem(item, empty);

            if (empty) {
                setText(null);
            } else {
                
                setText(item+"");
                int subtotal = produkList.stream().mapToInt(Produk::getHarga).sum();
                    subTotal.setText(subtotal+"");
            }
        }
    });
         
        aksiColumn.setCellFactory(col -> new TableCell<>() {
            final Button deleteButton = new Button("Delete");
            
            
            {
                deleteButton.setOnAction(event -> {
                    Produk order = getTableView().getItems().get(getIndex());
                    produkList.remove(order);
                });
               
                
                
            }
            
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    if(!isOrder)setGraphic(new HBox(deleteButton));
                }
            }
         
        });
         
         
         
         confirmButton.setOnAction((ActionEvent event) -> {
             boolean pay = (ordered!=null)?ordered.getStatus().equals("PAY"):false;
             if(!isOrder||pay){
                 
//             /Order(int id, int userId, String status, int subtotal) {
            String subTotalText = subTotal.getText();

            int subTotalValue = 0;

            if (subTotalText != null && !subTotalText.isEmpty()) {
                try {
                    subTotalValue = Integer.parseInt(subTotalText);
                } catch (NumberFormatException e) {
                    e.printStackTrace(); 
                }
            }

            Order order = new Order(0, user.getId(), null, subTotalValue);
            if(ordered!=null)order.setId(ordered.getId());
                        if(!pay) OrderModel.insertOrder(order, produkList);
                         try {


                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/pesanan/bayar.fxml"));
                                 Stage bayar = new Stage();
                                 bayar.initModality(Modality.APPLICATION_MODAL);
                                 bayar.setScene(new Scene(loader.load()));

                                 BayarController orderController = loader.getController();
                                 orderController.initSessionID(loginManager,user,order);
                                 
                                 bayar.showAndWait();
                                 bayar.close();

                                 tablePesanan.setItems(produkList);


                     } catch (IOException e) {
                         e.printStackTrace();
                     }
             }else{
                 OrderModel.deleteOrder(ordered.getId());
             }
             closePage();
          });
     }
      private void closePage() {
        // Close the current registration form
        ((Stage) dkk.getScene().getWindow()).close();

  
       
    }
}
