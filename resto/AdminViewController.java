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
import pesanan.ListOrderController;
import produk.KatalogController;
import produk.ListProdukController;

import user.User;



/** Controls the main application screen */
public class AdminViewController {
   @FXML
    private AnchorPane dkk;
   
    @FXML
    private Button manageProduk;
   
    @FXML
    private MenuItem logout;

   

    @FXML
    private Button managePesanan;

    @FXML
    private MenuButton menuButton;
  
  public void initialize() {}
  
  public void initSessionID(final LoginManager loginManager,User user) {
    menuButton.setText(user.getUsername());
    logout.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
        loginManager.logout();
      }
    });
    manageProduk.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
        try {
       FXMLLoader loader = new FXMLLoader(getClass().getResource("/produk/listproduk.fxml"));
        Stage produk = new Stage();
        produk.initModality(Modality.APPLICATION_MODAL);
        produk.setScene(new Scene(loader.load()));

        ListProdukController produkController = loader.getController();
         produkController.initSessionID(loginManager ,user);
       
        produk.show();

    } catch (IOException e) {
        e.printStackTrace();
    }
      }
    });
    
    
    managePesanan.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
      try {
       FXMLLoader loader = new FXMLLoader(getClass().getResource("/pesanan/listorder.fxml"));
        Stage listOrder = new Stage();
        listOrder.initModality(Modality.APPLICATION_MODAL);
        listOrder.setScene(new Scene(loader.load()));

        ListOrderController produkController = loader.getController();
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