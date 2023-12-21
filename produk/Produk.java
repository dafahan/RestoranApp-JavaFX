package produk;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Produk {

    private final SimpleIntegerProperty harga;
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty nama;
    private final SimpleStringProperty image;

    public Produk(int id, String nama, int harga, String image) {
        this.id = new SimpleIntegerProperty(id);
        this.nama = new SimpleStringProperty(nama);
        this.harga = new SimpleIntegerProperty(harga);
        this.image = new SimpleStringProperty(image);
    }

    // Getter methods
    public int getId() {
        return id.get();
    }

    public String getNama() {
        return nama.get();
    }

    public int getHarga() {
        return harga.get();
    }

    public String getImage() {
        return image.get();
    }

    // Setter methods
    public void setId(int id) {
        this.id.set(id);
    }

    public void setNama(String nama) {
        this.nama.set(nama);
    }

    public void setHarga(int harga) {
        this.harga.set(harga);
    }

    public void setImage(String image) {
        this.image.set(image);
    }
}
