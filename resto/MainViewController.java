package resto;

import java.io.IOException;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pesanan.OrderModel;
import pesanan.PesananController;
import produk.KatalogController;

import user.User;

/** Controls the main application screen */
public class MainViewController {
    
    public boolean cart = false;
    @FXML
    private AnchorPane dkk;

    @FXML
    private MenuItem logout;
    @FXML
    private MenuButton menuButton;
    
     @FXML
    private Button pesananButton;
   

    @FXML
    private Button catalogButton;

    @FXML
    private Button orderButton;
    
   
    
  public void initialize() {}
  
  public void initSessionID(final LoginManager loginManager,User user) {
    menuButton.setText(user.getUsername());
    logout.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
        loginManager.logout();
      }
    });
    
    orderButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
            boolean isOrder = OrderModel.isOrder(user.getId());
                        try {
               FXMLLoader loader = new FXMLLoader(getClass().getResource("/pesanan/pesanan.fxml"));
                Stage listOrder = new Stage();
                listOrder.initModality(Modality.APPLICATION_MODAL);
                listOrder.setScene(new Scene(loader.load()));

                PesananController produkController = loader.getController();
                produkController.isOrder = isOrder;
                produkController.ordered = OrderModel.getOrderByUser(user.getId());
                produkController.initSessionID(loginManager,user);

                // Show the katalog and wait for it to be closed
                listOrder.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
              }
    });
    
    
    catalogButton.setOnAction(new EventHandler<ActionEvent>() {
       
      @Override public void handle(ActionEvent event) {
          
      try {
       FXMLLoader loader = new FXMLLoader(getClass().getResource("/produk/katalog.fxml"));
        Stage katalog = new Stage();
        katalog.initModality(Modality.APPLICATION_MODAL);
        katalog.setScene(new Scene(loader.load()));

        KatalogController katalogController = loader.getController();
     

        // Show the katalog and wait for it to be closed
        katalog.show();

    } catch (IOException e) {
        e.printStackTrace();
    }
      }
    });
   
    
    
    pesananButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
          boolean isOrder = OrderModel.isOrder(user.getId());
                        try {
               FXMLLoader loader = new FXMLLoader(getClass().getResource("/pesanan/pesanan.fxml"));
                Stage listOrder = new Stage();
                listOrder.initModality(Modality.APPLICATION_MODAL);
                listOrder.setScene(new Scene(loader.load()));

                PesananController produkController = loader.getController();
                produkController.isOrder = isOrder;
                produkController.ordered = OrderModel.getOrderByUser(user.getId());
                produkController.initSessionID(loginManager,user);

                // Show the katalog and wait for it to be closed
                listOrder.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
      }
    });
  }
}