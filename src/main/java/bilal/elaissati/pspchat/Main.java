package bilal.elaissati.pspchat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistes/login.fxml"));
        Scene escena = new Scene(loader.load());
        escena.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        stage.setTitle("Login");
        stage.setScene(escena);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
