package resto;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import user.User;
import user.UserModel;

public class LoginController {
    @FXML private TextField user;
    @FXML private PasswordField password;   
    @FXML private Button loginButton;

    public void initialize() {}

    public void initManager(final LoginManager loginManager) {
        loginButton.setOnAction(event -> {
            String username = user.getText();
            String enteredPassword = password.getText(); 

            User authenticatedUser = UserModel.getUser(username, enteredPassword);

            if (authenticatedUser != null) {
                
                loginManager.authenticated(authenticatedUser);
            }
        });
    }
    @FXML
   
void registerHandler(ActionEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("register.fxml"));
        Parent registerForm = loader.load();

        RegisterController registerController = loader.getController();

           Stage registerStage = new Stage();
        registerStage.initModality(Modality.APPLICATION_MODAL);
        registerStage.setTitle("Register");
        registerStage.setScene(new Scene(registerForm));

        // Show the register form as a modal window
        registerStage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
}

}
