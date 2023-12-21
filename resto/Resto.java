
package resto;
import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;


/** Main application class for the login demo application */
public class Resto extends Application {
  public static void main(String[] args) { launch(args); }
  @Override public void start(Stage stage) throws IOException {
    Scene scene = new Scene(new StackPane());
    
    LoginManager loginManager = new LoginManager(scene);
    loginManager.showLoginScreen();

    stage.setScene(scene);
    stage.show();
  }
}
