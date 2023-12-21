
package resto;

import java.io.IOException;
import java.util.logging.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.layout.AnchorPane;
import user.User;

/** Manages control flow for logins */
public class LoginManager {
  private Scene scene;

  public LoginManager(Scene scene) {
    this.scene = scene;
  }

  /**
   * Callback method invoked to notify that a user has been authenticated.
   * Will show the main application screen.
     * @param user
   */ 
  public void authenticated(User user) {
    showMainView(user);
  }

  /**
   * Callback method invoked to notify that a user has logged out of the main application.
   * Will show the login application screen.
   */ 
  public void logout() {
    showLoginScreen();
  }
  
  public void showLoginScreen() {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent root = loader.load();
        scene.setRoot(root);

        LoginController controller = loader.getController();
        controller.initManager(this);
    } catch (IOException ex) {
        Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
    }
}


  private void showMainView(User user) {
    try {
        String frame = "mainview.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(frame));
        AnchorPane root = loader.load();
        Object controller = loader.getController();  
        
        if ("admin".equals(user.getRole())) {
            frame = "adminview.fxml";
            loader = new FXMLLoader(getClass().getResource(frame));
            root = loader.load();
            controller = loader.getController();
        }

        scene.setRoot(root);

       if (controller instanceof AdminViewController) {
           ((AdminViewController) controller).initSessionID(this, user);
       }
       else if (controller instanceof MainViewController) {
           ((MainViewController) controller).initSessionID(this, user);
        }

    } catch (IOException ex) {
        Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
    }

}

}
//dafahan.dev 2023