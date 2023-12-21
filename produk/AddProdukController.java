package produk;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import javafx.stage.Stage;
import static function.ProjectLocationDetector.getProjectLocation;
import javafx.collections.ObservableList;
import resto.LoginManager;
import user.User;

public class AddProdukController {
    boolean isUpdate = false;
    private ObservableList<Produk> roomList;
    int id = 0;
     private Produk a;
    @FXML
    private Button addImage;

    @FXML
    private AnchorPane dkk;

    @FXML
    private TextField hargaField;

    @FXML
    private ImageView imageView;

    @FXML
    private TextField namaField;

    @FXML
    private Button submit;
 private String imagePath; //
    public void setField(Produk produk){
        isUpdate = true;
        this.id = produk.getId();
        hargaField.setText(""+produk.getHarga());
        imagePath = produk.getImage();
         File file = new File(imagePath);
    Image image = new Image(file.toURI().toString());
        imageView.setImage(image);
        namaField.setText(produk.getNama());
        submit.setText("SIMPAN");
        
    }
    
   @FXML
     public ObservableList<Produk> initSessionID(final LoginManager loginManager,User user,  ObservableList<Produk> roomList) {
         this.roomList = roomList;
         submit.setOnAction((ActionEvent event) -> {
             ((Stage) dkk.getScene().getWindow()).close();
   
    
    int harga = Integer.parseInt(hargaField.getText());

    if (imagePath == null || imagePath.isEmpty()) {
        // Handle case where no image is selected
        System.out.println("Please select an image.");
        return;
    }
    
    Produk room = new Produk(id,namaField.getText(),  harga, imagePath);

    // Insert the room into the database using RoomModel
    if(!isUpdate){
    ProdukModel.insertProduk(room);
    }else{
     ProdukModel.updateProduk(room);
    }
    // Clear the text fields and reset the image view
    namaField.clear();
    hargaField.clear();
    imageView.setImage(null);
    imagePath = null;
   
        a = room;
       roomList.add(room);
       
         });
         
   return this.roomList;

}
      @FXML
void handleAddImage(ActionEvent event) throws IOException {
    // Open a file chooser to select an image
    FileChooser fileChooser = new FileChooser();
    fileChooser.getExtensionFilters().add(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
    File selectedFile = fileChooser.showOpenDialog(dkk.getScene().getWindow());

    if (selectedFile != null) {
        System.out.println(selectedFile);
        String uniqueImageName = generateUniqueImageName(selectedFile.getName());

        // Set the selected image in the ImageView
        if(!isUpdate){
        imagePath = "images/" + uniqueImageName; // Use the new directory
        }
        
        System.out.println(imagePath);
        Image image = new Image(selectedFile.toURI().toString());
        imageView.setImage(image);

        // Save the selected image to the desired path
        File destinationFile = new File(getProjectLocation() + "/" + imagePath);
        Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        System.out.println("Image saved to: " + destinationFile.getAbsolutePath());
    }
}


    private String generateUniqueImageName(String originalName) {
        // Get current timestamp
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String timestamp = now.format(formatter);

        // Generate a random UUID
        String uuid = UUID.randomUUID().toString().replace("-", "");

        // Extract the file extension from the original name
        String fileExtension = originalName.substring(originalName.lastIndexOf('.'));

        // Combine timestamp, UUID, and file extension to create a unique name
        return timestamp + uuid + fileExtension;
    }
Produk getRoom(){
    return this.a;
}
    @FXML
void handleSubmit(ActionEvent event) {
    
      
}
}
