package hellofx.SecondeApp.Controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import hellofx.Class.DataClass;
import hellofx.Class.Person;
import hellofx.Class.Ticket;
import hellofx.Class.Ticket;
import hellofx.Class.TicketDataClass;
import hellofx.FirstApp.Services.MainService;
import javafx.css.converter.StringConverter;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.util.converter.BooleanStringConverter;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.LocalDateStringConverter;
import javafx.util.converter.LongStringConverter;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.util.Callback;

public class TicketController {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private TableView<Ticket> ticketTableView;

    @FXML
    private TableColumn<Ticket, Long> TicketID;

    @FXML
    private TableColumn<Ticket, Integer> LicenseNumber;

    @FXML
    private TableColumn<Ticket, Integer> Speed;

    @FXML
    private TableColumn<Ticket, Integer> OfficerID;

    @FXML
    private TableColumn<Ticket, String> CarLicensePlate;

    @FXML
    private TableColumn<Ticket, Boolean> isOver80;

    @FXML
    private TableColumn<Ticket, Boolean> isSuspended;

    @FXML
    private TableColumn<Ticket, Double> Fine;

    @FXML
    private TableColumn<Ticket, LocalDate> TicketDate;

    @FXML
    private TextField searchField;

    @FXML
    private Button searchButton;

    @FXML
    private Button exportButton;

    @FXML
    private Button importButton;

    @FXML
    private Button payButton;

    private DataClass TicketDataClass = new DataClass();
    private TicketDataClass dataClass = new TicketDataClass();
    private MainService servie = new MainService();
    
    public void initialize() {
        // Initialize columns
        TicketID.setCellValueFactory(new PropertyValueFactory<>("ticketID"));
        LicenseNumber.setCellValueFactory(new PropertyValueFactory<>("licenseNumber"));
        Speed.setCellValueFactory(new PropertyValueFactory<>("speed"));
        OfficerID.setCellValueFactory(new PropertyValueFactory<>("officerID"));
        CarLicensePlate.setCellValueFactory(new PropertyValueFactory<>("carLicensePlate"));
        isOver80.setCellValueFactory(new PropertyValueFactory<>("isOver80"));
        isSuspended.setCellValueFactory(new PropertyValueFactory<>("isSuspended"));
        Fine.setCellValueFactory(new PropertyValueFactory<>("fine"));
        TicketDate.setCellValueFactory(new PropertyValueFactory<>("ticketDate"));

        Speed.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        Speed.setOnEditCommit(event -> {
            Ticket ticket = event.getRowValue();
            ticket.setSpeed(event.getNewValue());
            this.dataClass.updateDatabase(ticket);
        });
        OfficerID.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        OfficerID.setOnEditCommit(event -> {
            Ticket ticket = event.getRowValue();
            ticket.setOfficerID(event.getNewValue());
            this.dataClass.updateDatabase(ticket);
        });
        CarLicensePlate.setCellFactory(TextFieldTableCell.forTableColumn());
        CarLicensePlate.setOnEditCommit(event -> {
            Ticket ticket = event.getRowValue();
            ticket.setCarLicensePlate(event.getNewValue());
            this.dataClass.updateDatabase(ticket);
        });
        isOver80.setCellFactory(TextFieldTableCell.forTableColumn(new BooleanStringConverter()));
        isOver80.setOnEditCommit(event -> {
            Ticket ticket = event.getRowValue();
            ticket.setIsOver80(event.getNewValue());
            this.dataClass.updateDatabase(ticket);
        });
        isSuspended.setCellFactory(TextFieldTableCell.forTableColumn(new BooleanStringConverter()));
        isSuspended.setOnEditCommit(event -> {
            Ticket ticket = event.getRowValue();
            ticket.setIsSuspended(event.getNewValue());
            this.dataClass.updateDatabase(ticket);
        });
        Fine.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        Fine.setOnEditCommit(event -> {
            Ticket ticket = event.getRowValue();
            ticket.setFine(event.getNewValue());
            this.dataClass.updateDatabase(ticket);
        });
        TicketDate.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));
        TicketDate.setOnEditCommit(event -> {
            Ticket ticket = event.getRowValue();
            ticket.setTicketDate(event.getNewValue());
            this.dataClass.updateDatabase(ticket);
        });

        List<Ticket> tickets = dataClass.getSQLlist();
        ticketTableView.setEditable(true);
        ticketTableView.getItems().addAll(tickets);
    }
    

    


    @FXML
    public void Search(){
        if (this.searchField.getText().isEmpty()) {
            this.servie.Alert("Empty", "Search Field cannot be empty !", AlertType.WARNING);
            return ;
        }
        boolean found = false;
        Long searchId = -1L;
        try {
            searchId = Long.parseLong(this.searchField.getText());
        } catch (NumberFormatException e) {
            this.servie.Alert("Invalid Input", "Search only numbers, no characters!", AlertType.WARNING);
            return;
        }
        for (Ticket Ticket : this.ticketTableView.getItems()) {
            if (Ticket.getTicketID() == searchId) {
                ticketTableView.getSelectionModel().select(Ticket);
                ticketTableView.scrollTo(Ticket);
                found = true;
                break;
            }
        }
        if (!found) {
            this.servie.Alert("No Content", "Not Found!", AlertType.INFORMATION);
        }
    }

    @FXML
    public void goBack() throws IOException{
        this.servie.Loader("Tickets", "../../SecondeApp/Interfaces/Index.fxml",anchorPane);
    }
}

