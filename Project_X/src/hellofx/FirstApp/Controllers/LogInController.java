package hellofx.FirstApp.Controllers;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import hellofx.Class.DataClass;
import hellofx.Class.Person;
import hellofx.FirstApp.Services.MainService;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;


public class LogInController implements Initializable{

    private MainService Service = new MainService();

    //Login
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private PasswordField Password;
    @FXML
    private Button Unlock;
    private String code = "010101";



    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // Create a LinearGradient for the background
        LinearGradient gradient = new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
            new Stop(0, Color.web("#000000")), // Black
            new Stop(0.25, Color.web("#0000FF")), // Blue
            new Stop(0.5, Color.web("#FFFFFF")), // White
            new Stop(0.75, Color.web("#FF0000")), // Red
            new Stop(1, Color.web("#000000")));

        // Set the background of the AnchorPane
        anchorPane.setBackground(new Background(new BackgroundFill(gradient, null, null)));
    }

    @FXML
    public void Login() throws IOException{
        if (this.Password.getText()=="") {
            Service.Alert("Login Denied ", "Code Can't be empty !", AlertType.WARNING);
        }
        else{
            if (this.Password.getText().equalsIgnoreCase(code)) {
                Service.Alert("Login Successful", "Correct !", AlertType.CONFIRMATION);
                Service.Loader("Home", "../Interfaces/Home.fxml", anchorPane);
                }
                else{
                    Service.Alert("Login Denied ", "Wrong code !", AlertType.WARNING);
                }
        }
    }
}