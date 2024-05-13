package hellofx.FirstApp.Services;

import java.io.IOException;

import hellofx.Class.Person;
import hellofx.FirstApp.Controllers.TicketController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.Axis.TickMark;
import javafx.scene.control.Alert;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainService {

    public static void main(String[] args) {

    }

    public void Alert(String Ttitle, String message, AlertType Type) {
        Alert alert = new Alert(Type);
        alert.setTitle(Ttitle);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void Loader(String Title, String FXML, Pane pane) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML));
        Parent root1 = loader.load();
        Stage stage = (Stage) pane.getScene().getWindow(); // Get the current stage
        stage.setScene(new Scene(root1));
        stage.setTitle(Title);
    }

    public void Loader(String Title, String FXML, Pane pane, Person P) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML));
        loader.setController(new TicketController(P)); // Pass the Person object to the controller's constructor
        Parent root1 = loader.load();


        Stage stage = (Stage) pane.getScene().getWindow(); // Get the current stage
        stage.setScene(new Scene(root1));
        stage.setTitle(Title);
    }

    public void quit() {
        System.exit(0);
    }

}
