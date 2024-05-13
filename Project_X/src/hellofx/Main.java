package hellofx;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
    String app1 = "FirstApp/Interfaces/Interface.fxml";
    String app2 = "SecondeApp/Interfaces/Index.fxml";
    Parent root1 = FXMLLoader.load(getClass().getResource(app2));

        Scene scene = new Scene(root1);

        primaryStage.setScene(scene); 
        primaryStage.setTitle("TP6");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}   