package pesanan;

public class Order {
    private int id;
    private int user;
    private String status;
    private int subtotal;

    // Constructors
    public Order(int id, int userId, String status, int subtotal) {
        this.id = id;
        this.user = userId;
        this.status = status;
        this.subtotal = subtotal;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int userId) {
        this.user = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(int subtotal) {
        this.subtotal = subtotal;
    }
}

