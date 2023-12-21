package pesanan;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import produk.Produk;
import produk.ProdukModel;

import resto.LoginManager;
import user.User;

public class AddProdukController {
    
    @FXML
    private Button addButton;

    @FXML
    private AnchorPane dkk;

    @FXML
    private ComboBox<Produk> matkulComboBox;

    @FXML
    private TitledPane title;

    public ObservableList<Produk> initSessionID(final LoginManager loginManager, User user,ObservableList<Produk> produkList) {

        matkulComboBox.setConverter(new StringConverter<Produk>() {
            @Override
            public String toString(Produk matkul) {
                return matkul != null ? matkul.getNama() : null;
            }

            @Override
            public Produk fromString(String string) {
                // This method is not used in this example
                return null;
            }
        });

        // Set the items in the ComboBox
        matkulComboBox.getItems().addAll(ProdukModel.getAllProduk());

         addButton.setOnAction((ActionEvent event) -> {
             System.out.println("1");
            Produk selectedMatkul = matkulComboBox.getSelectionModel().getSelectedItem();
           
            if (selectedMatkul != null) {
                int matkulId = selectedMatkul.getId(); 
                boolean matkulIdExists = produkList.stream()
                        .anyMatch(produk -> produk.getId()==matkulId);

                
                  produkList.add(selectedMatkul);
                   closePage();
                
            }
        });
         
         return produkList;
         
         
    }
    private void closePage() {
        // Close the current registration form
        ((Stage) dkk.getScene().getWindow()).close();

  
       
    }
}
