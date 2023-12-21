package pesanan;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import resto.LoginManager;
import user.User;

public class BayarController {

    @FXML
    private Button confirm;

    @FXML
    private AnchorPane dkk;

    @FXML
    private Label idPesanan;

    @FXML
    private ChoiceBox<String> method;

    @FXML
    private TextField tanggal;

    @FXML
    private TextField total;

    @FXML
     public void initSessionID(final LoginManager loginManager,User user,Order order) {
        method.getItems().addAll("DANA", "GOPAY", "BRI", "MANDIRI", "QRIS", "CASH");
        total.setText(String.valueOf(order.getSubtotal()));
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        tanggal.setText(dateFormat.format(date));
        idPesanan.setText(String.valueOf(order.getId()));
        
         confirm.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
          order.setStatus("WAITING");
          OrderModel.updateOrder(order);
          closePage();
      }});
         
         
     }
    
     private void closePage() {
        ((Stage) dkk.getScene().getWindow()).close();
       
    }
}
