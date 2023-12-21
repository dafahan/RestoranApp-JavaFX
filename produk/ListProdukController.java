package produk;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;


import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import resto.LoginManager;
import user.User;


public class ListProdukController {
    
    @FXML
    private AnchorPane dkk;

    @FXML
    private TableColumn<Produk, String> aksiColumn;

    @FXML
    private Button addRoom;

    @FXML
    private TableColumn<Produk, Integer> idColumn;

    @FXML
    private TableColumn<Produk, Integer> hargaColumn;

    @FXML
    private TableColumn<Produk, String> namaColumn;

    @FXML
    private TableView<Produk> tableKamar;

    private ObservableList<Produk> roomList;

    @FXML
     public void initSessionID(final LoginManager loginManager,User user) {
         
         addRoom.setOnAction((ActionEvent event) -> {
                    
                FXMLLoader loader = new FXMLLoader(getClass().getResource("addProduk.fxml"));
        Stage addRoomStage = new Stage();
        addRoomStage.initModality(Modality.APPLICATION_MODAL);
             try {
                 addRoomStage.setScene(new Scene(loader.load()));
             } catch (IOException ex) {
                 Logger.getLogger(ListProdukController.class.getName()).log(Level.SEVERE, null, ex);
             }

        AddProdukController addRoomController = loader.getController();
       roomList = addRoomController.initSessionID(loginManager,user,roomList);

        addRoomStage.showAndWait();
           roomList = ProdukModel.getAllProduk();
         tableKamar.setItems(roomList);
        tableKamar.refresh();
             
         });
        idColumn.setCellValueFactory(cellData -> {
    // Get the current row index
    int rowIndex = cellData.getTableView().getItems().indexOf(cellData.getValue());

    // Display row number starting from 1
    return new ReadOnlyObjectWrapper<>(rowIndex + 1);
});
        hargaColumn.setCellValueFactory(new PropertyValueFactory<>("harga"));

       

        roomList = ProdukModel.getAllProduk();

        aksiColumn.setCellFactory(col -> new TableCell<>() {
            final Button deleteButton = new Button("Delete");
            final Button detailButton = new Button("Detail");

            {
                deleteButton.setOnAction(event -> {
                                       Produk room = getTableView().getItems().get(getIndex());
                    handleDeleteButton(room);
                });

             
                detailButton.setOnAction(event -> {
                    Produk room = getTableView().getItems().get(getIndex());
                    handleDetailButton(room,loginManager,user);
                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(new HBox(deleteButton, detailButton));
                }
            }
        });

        tableKamar.setItems(roomList);
        namaColumn.setCellValueFactory(new PropertyValueFactory<>("nama"));

        tableKamar.setRowFactory(new Callback<>() {
            @Override
            public TableRow<Produk> call(TableView<Produk> tableView) {
                return new TableRow<>() {
                    @Override
                    protected void updateItem(Produk item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setText(null);
                        } else {
                            setText(Integer.toString(getIndex() + 1));
                        }
                    }
                };
            }
        });
    }
    public void UpdateList() {
        // Logic to update the roomList, for example:
        roomList = ProdukModel.getAllProduk();
    }

    public void updateTable() {
        // Update the items in the table with the updated roomList
        tableKamar.setItems(roomList);
    }
    public void updateTable(Produk room) {
        
        roomList.add(room);
        tableKamar.setItems(roomList);
    }
    @FXML
    void handleAddButton(ActionEvent event) throws IOException {
        
            
      //  }
    }
    
       
    
   private void handleDeleteButton(Produk room) {
    System.out.println("Delete room: " + room.getId());
    ProdukModel.deleteProduk(room.getId());

    roomList.remove(room);
}




    private void handleDetailButton(Produk room,LoginManager loginManager , User user) {
        //System.out.println("Detail of room: " + room.getIdKamar());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addProduk.fxml"));
        Stage addRoomStage = new Stage();
        addRoomStage.initModality(Modality.APPLICATION_MODAL);
        try {
            addRoomStage.setScene(new Scene(loader.load()));
        } catch (IOException ex) {
            Logger.getLogger(ListProdukController.class.getName()).log(Level.SEVERE, null, ex);
        }
        AddProdukController addRoomController = loader.getController();
        addRoomController.setField(room);
        addRoomController.initSessionID(loginManager,user,roomList);
        addRoomStage.showAndWait();
        roomList = ProdukModel.getAllProduk();
        tableKamar.setItems(roomList);
        tableKamar.refresh();
    }
}

