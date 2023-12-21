package produk;

import java.io.File;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class KatalogController {
    private int page = 1;
    private List<Produk> roomList;  // List to store rooms
   
    private int totalItems;       // Total number of items


    @FXML
    private AnchorPane dkk;

    @FXML
    private ImageView imageView;

    @FXML
    private Button next;

    @FXML
    private Button prev;

    @FXML
    private TextField title;
    @FXML
    private TextField hargaField;

    @FXML
    public void initialize() {
        
        loadRoomList();

        // Display the first page
        showPage(page);
    }

    
    

    @FXML
    void handleNext(ActionEvent event) {
        // Move to the next page and update the display
        if (page < totalItems ) {
            page++;
            showPage(page);
        }else{
            page = 1;
            showPage(page);
        }
    }

    @FXML
    void handlePrev(ActionEvent event) {
        // Move to the previous page and update the display
        if (page > 1) {
            page--;
            showPage(page);
        }else{
            page = totalItems;
            showPage(page);
        }
    }

    private void loadRoomList() {
        // Load the entire list of rooms from the database
        roomList = ProdukModel.getAllProduk();
        totalItems = roomList.size();
    }

    private void showPage(int currentPage) {
       
       Produk cur = roomList.get(currentPage-1);
        System.out.println(cur.getId());
        File file = new File(cur.getImage());
    Image image = new Image(file.toURI().toString());
            title.setText(cur.getNama());
            System.out.println(cur.getImage());
    imageView.setImage(image);
    hargaField.setText(cur.getHarga()+"");
    }
}
