package hellofx.FirstApp.Controllers;

import hellofx.Class.Person;
import hellofx.Class.Ticket;
import hellofx.FirstApp.Services.MainService;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.PrimitiveIterator;
import java.util.ResourceBundle;
import java.time.Period;
import java.time.LocalDate;
import java.time.ZoneId;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.util.converter.LocalDateStringConverter;

public class TicketController implements Initializable {

    private MainService Service = new MainService();

    private Person Person;

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private ChoiceBox<String> ticketTypeChoiceBox;
    @FXML
    private Label IDLabel;
    @FXML
    private Label ID;
    @FXML
    private Label LicenseDate;
    @FXML
    private Label LicenseNumber;
    @FXML
    private Label FirstName;
    @FXML
    private Label LastName;
    @FXML
    private CheckBox over80CheckBox;
    @FXML
    private Label over80CheckBoxLabel;
    @FXML
    private TextField CarLicensePlate;
    @FXML
    private TextField OfficerID;
    @FXML
    private Button Addbtn;
    @FXML
    private Button quitbtn;


    public TicketController(Person person) {
        this.Person = person;
    }

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

        if (this.Person != null) {
            IDLabel.setText("Ticket To: " + this.Person.getFirst_Name() + " " + this.Person.getLast_Name());
            FirstName.setText(this.Person.getFirst_Name());
            LastName.setText(this.Person.getLast_Name());
            LocalDate date = this.Person.getLicenseDate();
            LicenseDate.setText(date.toString());
            LicenseNumber.setText(String.valueOf(this.Person.getLicenseNumber()));
            ID.setText(String.valueOf(this.Person.getID()));

        } else {
            IDLabel.setText("Unknown");
        }

        ticketTypeChoiceBox.getItems().addAll(
                "20",
                "30",
                "40",
                "+50");

        ticketTypeChoiceBox.setValue("20");

        LocalDate currentDate = LocalDate.now();
        LocalDate licenseDate = this.Person.getLicenseDate();
        Period period = Period.between(licenseDate, currentDate);
        if (period.getYears() < 2 ) {
            over80CheckBox.setVisible(true);
            over80CheckBoxLabel.setVisible(true);
        }
            
    }




    @FXML
    public void AddTicket(){
        Ticket ticket = new Ticket();
        if (this.OfficerID.getText().isEmpty() || this.CarLicensePlate.getText().isEmpty()) {
            this.Service.Alert("Empty Field", "Car License Plate and \nOfficer id cannot be empty", AlertType.WARNING);
        }
        else{
            ticket.setLicenseNumber(this.Person.getLicenseNumber());
            ticket.setCarLicensePlate(this.CarLicensePlate.getText());
            ticket.setIsOver80(this.over80CheckBox.isSelected());
            ticket.setOfficerID(Integer.parseInt(this.OfficerID.getText()));
            ticket.setTicketDate(LocalDate.now());
            String ticketID = ticket.getLicenseNumber() +
            ticket.getOfficerID() +
            ticket.getTicketDate().getYear() +
            String.format("%02d", ticket.getTicketDate().getDayOfMonth()) +
            String.format("%02d", ticket.getTicketDate().getMonthValue());

            ticket.setTicketID(Long.parseLong(ticketID));

            if (this.ticketTypeChoiceBox.getSelectionModel().getSelectedItem() == "+50") {
                ticket.setIsSuspended(true);
                ticket.setFine(1000);
                ticket.setSpeed(50);
            }
            else if (ticket.isIsOver80()) {
                ticket.setIsSuspended(true);
                ticket.setFine(1000);
                ticket.setSpeed(Integer.parseInt(this.ticketTypeChoiceBox.getSelectionModel().getSelectedItem()));
            }
            else{
                ticket.setSpeed(Integer.parseInt(this.ticketTypeChoiceBox.getSelectionModel().getSelectedItem()));
                if (ticket.getSpeed() == 20) {
                    ticket.setFine(60);
                }
                else if (ticket.getSpeed() == 30){
                    ticket.setFine(120);
                }
                else{
                    ticket.setFine(500);
                }
            }
            boolean p = false;
            String filePath = "Data/Tickets.txt";
            try {
                FileReader fileReader = new FileReader(filePath);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    String[] data = line.split("#");
                    if (Long.parseLong(data[0])==ticket.getTicketID()) {
                        this.Service.Alert("Error", "Ticket Already Exists !", AlertType.WARNING);
                        p=true;
                        break;
                    }
                }
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (!p) {        
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath,true))) {
                    
                    writer.write(ticket.toString());
                    writer.flush();
                    this.Service.Alert("Success", "Successfuly added the ticket !", AlertType.CONFIRMATION);
                    this.Service.Loader("Home", "../Interfaces/Home.fxml", anchorPane);
                    
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @FXML
    public void quit() {
        this.Service.quit();
    }
}
