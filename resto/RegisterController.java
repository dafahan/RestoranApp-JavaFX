package resto;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import user.UserModel;

public class RegisterController {

    @FXML
    private AnchorPane dkk;

    @FXML
    private Hyperlink login;

    @FXML
    private TextField password;

    @FXML
    private Button registerButton;

    @FXML
    private TextField user;

    @FXML
    void initialize() {
        // Set the registerHandler method as the action for the "Register" button
        registerButton.setOnAction(event -> registerHandler());

        // Set the loginHandler method as the action for the "Login" hyperlink
        login.setOnAction(event -> loginHandler());
    }

    private void registerHandler() {
    String username = user.getText();
    String enteredPassword = password.getText();

    // Perform registration logic using UserModel
    boolean registrationSuccess = UserModel.registerUser(username, enteredPassword);

    Alert alert = new Alert(AlertType.INFORMATION);

    if (registrationSuccess) {
        // Handle successful registration
        alert.setTitle("Registration Successful");
        alert.setHeaderText(null);
        alert.setContentText("Registration successful!");
    } else {
        // Handle registration failure
        alert.setTitle("Registration Failed");
        alert.setHeaderText(null);
        alert.setContentText("Registration failed. Please try again.");
    }

    // Show the alert
    alert.showAndWait();
    ((Stage) dkk.getScene().getWindow()).close();
}

    private void loginHandler() {
        // Close the current registration form
        ((Stage) dkk.getScene().getWindow()).close();

  
       
    }
}

