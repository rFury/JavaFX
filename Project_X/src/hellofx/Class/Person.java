package hellofx.Class;

import java.time.LocalDate;
import java.util.Date;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;

public class Person {
    
    private String First_Name;
    private String Last_Name;
    private int LicenseNumber;
    private int ID;
    private LocalDate LicenseDate;
    private ChoiceBox<Long> tickets;


    public Person() {
    }
    public Person(String first_Name, String last_Name, int licenseNumber, int iD, LocalDate licenseDate) {
        First_Name = first_Name;
        Last_Name = last_Name;
        LicenseNumber = licenseNumber;
        ID = iD;
        LicenseDate = licenseDate;
    }

    public Person(String first_Name, String last_Name, int licenseNumber, int iD, LocalDate licenseDate,ChoiceBox<Long> tableView) {
        First_Name = first_Name;
        Last_Name = last_Name;
        LicenseNumber = licenseNumber;
        ID = iD;
        LicenseDate = licenseDate;
        this.tickets = tableView;
    }
    public String getFirst_Name() {
        return First_Name;
    }
    public ChoiceBox<Long> getTickets() {
        return tickets;
    }
    public void setTickets(ChoiceBox<Long> tickets) {
        this.tickets = tickets;
    }
    public void setFirst_Name(String first_Name) {
        First_Name = first_Name;
    }
    public String getLast_Name() {
        return Last_Name;
    }
    public void setLast_Name(String last_Name) {
        Last_Name = last_Name;
    }
    public int getLicenseNumber() {
        return LicenseNumber;
    }
    public void setLicenseNumber(int licenseNumber) {
        LicenseNumber = licenseNumber;
    }
    public int getID() {
        return ID;
    }
    public void setID(int iD) {
        ID = iD;
    }
    public LocalDate getLicenseDate() {
        return LicenseDate;
    }
    public void setLicenseDate(LocalDate licenseDate) {
        LicenseDate = licenseDate;
    }
    @Override
    public String toString() {
        return this.First_Name + "#" +
                this.Last_Name + "#" +
                this.LicenseNumber + "#" +
                this.ID + "#" +
                this.LicenseDate+"\n" ; 
    }
    



}
