package hellofx.SecondeApp.Controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.security.auth.callback.ConfirmationCallback;

import java.time.temporal.ChronoUnit;
import hellofx.Class.DataClass;
import hellofx.Class.Person;
import hellofx.Class.Ticket;
import hellofx.Class.TicketDataClass;
import hellofx.FirstApp.Services.MainService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class TicketPaymentController {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private TableView<Person> ticketTableView;

    @FXML
    private TableColumn<Person, String> first_NameColumn;

    @FXML
    private TableColumn<Person, String> last_NameColumn;

    @FXML
    private TableColumn<Person, Integer> licenseNumberColumn;

    @FXML
    private TableColumn<Person, Integer> idColumn;

    @FXML
    private TableColumn<Person, Date> licenseDateColumn;

    @FXML
    private TableColumn<Person, ChoiceBox<Long>> ticketsColumn; // Nested TableView column

    @FXML
    private TextField searchField;

    @FXML
    private Button searchButton;

    @FXML
    private Button exportButton;

    @FXML
    private Button importButton;

    @FXML
    private Button importButton1;

    @FXML
    private Button payButton;

    @FXML
    private Label Ticketlabel;

    @FXML
    private Label Finelabel;

    @FXML
    private TextField hiddenTextField;

    @FXML
    private TextField hiddenTextField1;

    private Long SelectedTicketID = 0L;
    private DataClass PersonDataClass = new DataClass();
    private TicketDataClass dataClass = new TicketDataClass();
    private MainService servie = new MainService();

    boolean x = false;

    public interface ConfirmationCallback {
        void onConfirm();

        void onCancel();
    }

    public void initialize() throws IOException {
        // Initialize columns
        first_NameColumn.setCellValueFactory(new PropertyValueFactory<>("first_Name"));
        last_NameColumn.setCellValueFactory(new PropertyValueFactory<>("last_Name"));
        licenseNumberColumn.setCellValueFactory(new PropertyValueFactory<>("licenseNumber"));
        idColumn.setCellValueFactory(new PropertyValueFactory<>("iD"));
        licenseDateColumn.setCellValueFactory(new PropertyValueFactory<>("licenseDate"));
        // Initialize nested TableView column
        ticketsColumn.setCellValueFactory(new PropertyValueFactory<>("tickets"));
        ticketsColumn.setCellFactory(param -> new TableCell<Person, ChoiceBox<Long>>() {
            @Override
            protected void updateItem(ChoiceBox<Long> item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText("No Tickets Available !");
                } else if (item == null || item.getItems().isEmpty()) {
                    setText("No Tickets Available !");
                } else {
                    setGraphic(item);
                }
            }
        });

        List<Person> persons = this.getPPL();
        ticketTableView.getItems().addAll(persons);

    }

    private ChoiceBox<Long> createTicketTableView(List<Ticket> tickets) {
        ChoiceBox<Long> ChoiceBox = new ChoiceBox<Long>();
        for (Ticket ticket : tickets) {
            ChoiceBox.getItems().add(ticket.getTicketID());
        }
        ChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Call the getSelected method with the selected ticket ID
                this.SelectedTicketID = newValue;
                this.getSelected(newValue);
            }
        });

        return ChoiceBox;
    }

    public void getSelected(Long newValue) {
        this.Finelabel.setVisible(true);
        this.Ticketlabel.setVisible(true);
        this.hiddenTextField.setVisible(true);
        this.hiddenTextField1.setVisible(true);

        try {
            Ticket T = this.dataClass.getTicket(newValue);
            this.hiddenTextField.setText("TicketID: " + T.getTicketID() + " | "
                    + "License Number: " + T.getLicenseNumber() + " | "
                    + "Date: " + T.getTicketDate() + " | "
                    + "Officer ID" + T.getOfficerID());
            Long weeks = LocalDate.now().until(T.getTicketDate(), ChronoUnit.WEEKS);
            Long overdue = Math.abs(weeks / 2);
            System.out.println(overdue);
            if (overdue > 1) {
                T.setFine(overdue * T.getFine());
            }
            this.hiddenTextField1.setText(String.valueOf(T.getFine()));

            System.out.println(T);

        } catch (Exception e) {
            System.out.println("get selected :" + e.getMessage() + " " + e.getCause());
        }

    }

    @FXML
    public void importTickets() throws IOException {
        if (!x) {
            this.dataClass.processTickets();
            this.ticketTableView.getItems().clear();
            this.ticketTableView.refresh();
            this.ticketTableView.getItems().addAll(this.getPPL());
            x = true;
        } else {
            this.servie.Alert("Warning", "Tickets Already Imported !", AlertType.WARNING);
        }

    }

    @FXML
    public void export() throws IOException {
        this.PersonDataClass.exportSQLList();
    }

    @FXML
    public void Search() {
        if (this.searchField.getText().isEmpty()) {
            this.servie.Alert("Empty", "Search Field cannot be empty !", AlertType.WARNING);
            return;
        }
        boolean found = false;
        int searchId = -1;
        try {
            searchId = this.PersonDataClass.getTicketOwner(Long.parseLong(this.searchField.getText()));
            if (searchId == 0) {
                this.servie.Alert("No Content", "Not Found!", AlertType.INFORMATION);
                return;
            }
        } catch (NumberFormatException e) {
            this.servie.Alert("Invalid Input", "Search only numbers, no characters!", AlertType.WARNING);
            return;
        }
        for (Person person : this.ticketTableView.getItems()) {
            if (person.getID() == searchId) {
                ticketTableView.getSelectionModel().select(person);
                ticketTableView.scrollTo(person);
                found = true;
                break; // No need to continue searching once found
            }
        }
        if (!found) {
            this.servie.Alert("No Content", "Not Found!", AlertType.INFORMATION);
        }
    }

    public List<Person> getPPL() {
        List<Person> persons = PersonDataClass.getSQList();
        for (Person person : persons) {
            List<Ticket> tickets = dataClass.getTicketPerPerson(person.getLicenseNumber());
            System.out.println("person id " + person.getID() + "Name" + person.getFirst_Name() + " "
                    + person.getLast_Name() + tickets);
            person.setTickets(createTicketTableView(tickets));
        }
        return persons;
    }

    @FXML
    public void payTicket() {
        if (this.SelectedTicketID == 0) {
            this.servie.Alert("Erreur", "No ticket Selected !", AlertType.WARNING);
        } else {
            ConfirmationCallback callback = new ConfirmationCallback() {
                @Override
                public void onConfirm() {
                    proceedWithPayment();
                }

                @Override
                public void onCancel() {
                    for (Person person : ticketTableView.getItems()) {
                        ChoiceBox<Long> choiceBox = person.getTickets();
                        if (choiceBox != null) {
                            choiceBox.getSelectionModel().clearSelection();
                        }
                    }
                    Finelabel.setVisible(false);
                    Ticketlabel.setVisible(false);
                    hiddenTextField.setVisible(false);
                    hiddenTextField1.setVisible(false);
                }
            };
            displayConfirmationDialog(callback);
        }
    }

    private void displayConfirmationDialog(ConfirmationCallback callback) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Are you sure you want to proceed with payment?");
        alert.setContentText("Please confirm.");

        ButtonType confirmButton = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(confirmButton, cancelButton);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == confirmButton) {
            callback.onConfirm();
        } else {
            callback.onCancel();
        }
    }

    private void proceedWithPayment() {
        this.dataClass.deleteTicket(this.SelectedTicketID);
        for (Person person : ticketTableView.getItems()) {
            ChoiceBox<Long> choiceBox = person.getTickets();
            if (choiceBox.getItems().contains(this.SelectedTicketID)) {
                choiceBox.getItems().remove(this.SelectedTicketID);
                choiceBox.getSelectionModel().clearSelection();
                this.ticketTableView.refresh();
            }
        }
    }

    @FXML
    public void Tickets() throws IOException {
        this.servie.Loader("Tickets", "../../SecondeApp/Interfaces/Tickets.fxml",anchorPane);
    }

}
