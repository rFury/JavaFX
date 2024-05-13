package hellofx.Class;

import java.time.LocalDate;

public class Ticket {

    private Long TicketID;
    private int LicenseNumber;
    private int Speed;
    private int OfficerID;
    private String CarLicensePlate;
    private boolean IsOver80 = false;
    private boolean IsSuspended = false;
    private LocalDate TicketDate;
    private double Fine;

    public double getFine() {
        return Fine;
    }

    public void setFine(double fine) {
        Fine = fine;
    }

    public Ticket(Long ticketID, int licenseNumber, int speed, int officerID, String carLicensePlate,
            boolean isSuspended) {
        TicketID = ticketID;
        LicenseNumber = licenseNumber;
        Speed = speed;
        OfficerID = officerID;
        CarLicensePlate = carLicensePlate;
        IsSuspended = isSuspended;
    }

    public LocalDate getTicketDate() {
        return TicketDate;
    }

    public void setTicketDate(LocalDate ticketDate) {
        TicketDate = ticketDate;
    }

    public Ticket(Long ticketID, int licenseNumber, int speed, int officerID, String carLicensePlate, boolean isOver80,
            boolean isSuspended) {
        TicketID = ticketID;
        LicenseNumber = licenseNumber;
        Speed = speed;
        OfficerID = officerID;
        CarLicensePlate = carLicensePlate;
        IsOver80 = isOver80;
        IsSuspended = isSuspended;
    }

    public Ticket(Long ticketID, int licenseNumber, int speed, int officerID, String carLicensePlate, boolean isOver80,
            boolean isSuspended , double fine, LocalDate ticketDate) {
        TicketID = ticketID;
        LicenseNumber = licenseNumber;
        Speed = speed;
        OfficerID = officerID;
        CarLicensePlate = carLicensePlate;
        IsOver80 = isOver80;
        IsSuspended = isSuspended;
        TicketDate = ticketDate;
        Fine = fine;
    }
    
    public Ticket() {
    }

    public Long getTicketID() {
        return TicketID;
    }

    public void setTicketID(Long ticketID) {
        TicketID = ticketID;
    }

    public int getLicenseNumber() {
        return LicenseNumber;
    }

    public void setLicenseNumber(int licenseNumber) {
        LicenseNumber = licenseNumber;
    }

    public int getSpeed() {
        return Speed;
    }

    public void setSpeed(int speed) {
        Speed = speed;
    }

    public int getOfficerID() {
        return OfficerID;
    }

    public void setOfficerID(int officerID) {
        OfficerID = officerID;
    }

    public String getCarLicensePlate() {
        return CarLicensePlate;
    }

    public void setCarLicensePlate(String carLicensePlate) {
        CarLicensePlate = carLicensePlate;
    }

    public boolean isIsOver80() {
        return IsOver80;
    }

    public void setIsOver80(boolean isOver80) {
        IsOver80 = isOver80;
    }

    public boolean isIsSuspended() {
        return IsSuspended;
    }

    public void setIsSuspended(boolean isSuspended) {
        IsSuspended = isSuspended;
    }

    @Override
    public String toString() {
        return TicketID +
                "#"+LicenseNumber +
                "#" + Speed +
                "#" + OfficerID +
                "#" + CarLicensePlate +
                "#" + IsOver80 +
                "#" + IsSuspended +
                "#" + Fine +
                "#" + TicketDate +
                "\n";

    }
}
