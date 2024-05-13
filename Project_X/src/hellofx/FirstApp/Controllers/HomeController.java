package hellofx.FirstApp.Controllers;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import hellofx.Class.DataClass;
import hellofx.Class.Person;
import hellofx.FirstApp.Services.MainService;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.cell.PropertyValueFactory;



public class HomeController implements Initializable{

    private MainService Service = new MainService();

    //Login
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Menu Licenses;
    @FXML
    private TextField SearchInput;
    @FXML
    private Button addbtn;
    @FXML
    private Button exportbtn;
    @FXML
    private Button importbtn;
    @FXML
    private Button removebtn;
    @FXML
    private Button Searchbtn;
    @FXML
    private Button quitbtn;
    @FXML
    private TableView<Person> table;
    @FXML
    private TableColumn<Person, Integer> IDCol;
    @FXML
    private TableColumn<Person, String> FirstNameCol;
    @FXML
    private TableColumn<Person, String> LastNameCol;
    @FXML
    private TableColumn<Person, Integer> LicenseNumberCol;
    @FXML
    private TableColumn<Person, Date> LicenseDateCol;
    @FXML
    private TableColumn<Person, Integer> SuspendCol;
    @FXML
    private TableColumn<Person, Character> TicketCol;

    private DataClass data;

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
        this.data = new DataClass();
    }

    @FXML
    public void importLicenses() {
        List<Person> list = this.data.getImportList();
        System.out.println(list.size());
        
        // Clear existing items in the table
        this.table.getItems().clear();
    
        // Add items to the table column by column
        for (Person person : list) {
            this.table.getItems().add(person);
            IDCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
            LicenseNumberCol.setCellValueFactory(new PropertyValueFactory<>("LicenseNumber"));
            LicenseDateCol.setCellValueFactory(new PropertyValueFactory<>("LicenseDate"));
            FirstNameCol.setCellValueFactory(new PropertyValueFactory<>("First_Name"));
            LastNameCol.setCellValueFactory(new PropertyValueFactory<>("Last_Name"));
        }
    }

    @FXML
    public void AddTicket() throws IOException{
        Person selectedPerson = table.getSelectionModel().getSelectedItem();

        if (selectedPerson != null) {
            this.Service.Loader("Ticket", "../Interfaces/Ticket.fxml", anchorPane,selectedPerson);
        }
        else{
            this.Service.Alert("No Content", "No Person Selected !", AlertType.WARNING);
        }
    } 
    
    @FXML
    public void Search(){
        if (this.SearchInput.getText().isEmpty()) {
            this.Service.Alert("No Content", "Field is empty !", AlertType.WARNING);
            return;
        }
        boolean found = false;
        int searchId = -1;
        try {
            searchId = Integer.parseInt(this.SearchInput.getText());
        } catch (NumberFormatException e) {
            this.Service.Alert("Invalid Input", "Search only numbers, no characters!", AlertType.WARNING);
            return;
        }
        for (Person person : this.table.getItems()) {
            if (person.getID() == searchId) {
                table.getSelectionModel().select(person);
                table.scrollTo(person);
                found = true;
                break; // No need to continue searching once found
            }
        }
        if (!found) {
            this.Service.Alert("No Content", "Not Found!", AlertType.INFORMATION);
        }
    }
    

    @FXML
    public void LoadTickets() throws IOException{
        this.Service.Loader("Ticket", "../Interfaces/Ticket.fxml", anchorPane);
    }

    @FXML
    public void quit() {
        this.Service.quit();
    }

}
