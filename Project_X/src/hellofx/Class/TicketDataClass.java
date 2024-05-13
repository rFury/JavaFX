package hellofx.Class;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.protocol.Resultset;
import com.mysql.cj.xdevapi.SelectStatement;

public class TicketDataClass {
    private List<Ticket> importList;
    private List<Ticket> exportList;

    private List<Ticket> importListTickets;
    private List<Ticket> exportListTickets;

    private MySQLConnection connection = new MySQLConnection();
    String filePath = "Data/Tickets.txt";

    public TicketDataClass() {
        this.importListTickets = new ArrayList<Ticket>();
        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split("#");
                try {
                    LocalDate date = LocalDate.parse(data[8]);
                    Ticket C = new Ticket(Long.parseLong(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2]),
                            Integer.parseInt(data[3]), data[4], Boolean.parseBoolean(data[5]),
                            Boolean.parseBoolean(data[6]), Double.parseDouble(data[7]), date);
                    this.importListTickets.add(C);
                } catch (Exception e) {
                }
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Ticket> getImportList() {
        return importListTickets;
    }

    public List<Ticket> getExportList() {
        return exportListTickets;
    }

    public void setExportList(List<Ticket> exportList) {
        this.exportListTickets.addAll(exportList);
        for (Ticket p : this.exportList)
            System.out.println(p);
    }

    public void processTickets() throws IOException {
        try (Connection connect = connection.getConnection()) {
            String query = "SELECT * FROM TICKET WHERE TICKETID = ?";
            String insertQuery = "INSERT INTO TICKET (TICKETID, LicenseNumber, Speed, OfficerID, CarLicensePlate, IsOver80, IsSuspended, Fine, TicketDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement selectStatement = connect.prepareStatement(query);
                    PreparedStatement insertStatement = connect.prepareStatement(insertQuery)) {
                for (Ticket ticket : importListTickets) {
                    long ticketId = ticket.getTicketID();
                    selectStatement.setLong(1, ticketId);

                    try (ResultSet resultSet = selectStatement.executeQuery()) {
                        if (!resultSet.next()) {
                            insertStatement.setLong(1, ticket.getTicketID());
                            insertStatement.setInt(2, ticket.getLicenseNumber());
                            insertStatement.setInt(3, ticket.getSpeed());
                            insertStatement.setInt(4, ticket.getOfficerID());
                            insertStatement.setString(5, ticket.getCarLicensePlate());
                            insertStatement.setBoolean(6, ticket.isIsOver80());
                            insertStatement.setBoolean(7, ticket.isIsSuspended());
                            insertStatement.setDouble(8, ticket.getFine());
                            insertStatement.setDate(9, java.sql.Date.valueOf(ticket.getTicketDate()));

                            int rowsAffected = insertStatement.executeUpdate();
                            if (rowsAffected > 0) {
                                BufferedWriter deleter = new BufferedWriter(new FileWriter(filePath));
                            } else {
                                System.out.println("Insert failed");
                            }

                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQLException appropriately
        }
    }

    public List<Ticket> getTicketPerPerson(int licenseNumber) {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection connect = connection.getConnection()) {
            String query = "SELECT * FROM Ticket WHERE LicenseNumber = ?";
            try (PreparedStatement selectStatement = connect.prepareStatement(query)) {
                selectStatement.setInt(1, licenseNumber);

                try (ResultSet resultSet = selectStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Ticket ticket = new Ticket();
                        ticket.setTicketID(resultSet.getLong("ticketID"));
                        ticket.setLicenseNumber(resultSet.getInt("LicenseNumber"));
                        ticket.setSpeed(resultSet.getInt("Speed"));
                        ticket.setOfficerID(resultSet.getInt("OfficerID"));
                        ticket.setCarLicensePlate(resultSet.getString("CarLicensePlate"));
                        ticket.setIsOver80(resultSet.getBoolean("IsOver80"));
                        ticket.setIsSuspended(resultSet.getBoolean("IsSuspended"));
                        ticket.setFine(resultSet.getDouble("Fine"));
                        ticket.setTicketDate(resultSet.getDate("TicketDate").toLocalDate());

                        tickets.add(ticket);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQLException appropriately
        }
        return tickets;
    }

    public List<Ticket> getSQLlist() {
        List<Ticket> tickets = new ArrayList<>();
        try {

            Connection connect = connection.getConnection();

            Statement statement = connect.createStatement();

            // Execute query
            ResultSet resultSet = statement.executeQuery("SELECT * FROM ticket");
            while (resultSet.next()) {
                Ticket ticket = new Ticket();
                ticket.setTicketID(resultSet.getLong("ticketID"));
                ticket.setLicenseNumber(resultSet.getInt("LicenseNumber"));
                ticket.setSpeed(resultSet.getInt("Speed"));
                ticket.setOfficerID(resultSet.getInt("OfficerID"));
                ticket.setCarLicensePlate(resultSet.getString("CarLicensePlate"));
                ticket.setIsOver80(resultSet.getBoolean("IsOver80"));
                ticket.setIsSuspended(resultSet.getBoolean("IsSuspended"));
                ticket.setFine(resultSet.getDouble("Fine"));
                ticket.setTicketDate(resultSet.getDate("TicketDate").toLocalDate());

                tickets.add(ticket);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return tickets;
    }

    public void updateDatabase(Ticket ticket) {
        try (Connection connect = connection.getConnection()) {
            String query = "UPDATE TICKET SET SPEED = ?, officerid = ?, carlicenseplate = ?, isover80 = ?, isSuspended = ?, fine = ?, ticketDate = ? WHERE TICKETID = ?";
            try (PreparedStatement insertStatement = connect.prepareStatement(query)) {
                long ticketId = ticket.getTicketID();
                insertStatement.setLong(8, ticketId);
                insertStatement.setInt(1, ticket.getSpeed());
                insertStatement.setInt(2, ticket.getOfficerID());
                insertStatement.setString(3, ticket.getCarLicensePlate());
                insertStatement.setBoolean(4, ticket.isIsOver80());
                insertStatement.setBoolean(5, ticket.isIsSuspended());
                insertStatement.setDouble(6, ticket.getFine());
                insertStatement.setDate(7, java.sql.Date.valueOf(ticket.getTicketDate()));

                insertStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQLException appropriately
        }
    }

    public Ticket getTicket(Long L) {
        Ticket ticket = new Ticket();
    
        try (Connection connect = connection.getConnection()) {
            // Execute query
            String query = "SELECT * FROM ticket WHERE TICKETID = ?";
            try (PreparedStatement selectStatement = connect.prepareStatement(query)) {
                selectStatement.setLong(1, L);
                try (ResultSet resultSet = selectStatement.executeQuery()) {
                    // Check if the result set has any rows
                    if (resultSet.next()) {
                        ticket.setLicenseNumber(resultSet.getInt("LicenseNumber"));
                        ticket.setSpeed(resultSet.getInt("Speed"));
                        ticket.setOfficerID(resultSet.getInt("OfficerID"));
                        ticket.setCarLicensePlate(resultSet.getString("CarLicensePlate"));
                        ticket.setIsOver80(resultSet.getBoolean("IsOver80"));
                        ticket.setIsSuspended(resultSet.getBoolean("IsSuspended"));
                        ticket.setFine(resultSet.getDouble("Fine"));
                        ticket.setTicketDate(resultSet.getDate("TicketDate").toLocalDate());
                        ticket.setTicketID(L);
                    } else {
                        // Handle case where no ticket with the given ID is found
                        // You might throw an exception or return null depending on your use case
                        throw new SQLException("No ticket found with ID: " + L);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching ticket: " + e.getMessage()); // Re-throw the exception to be handled by the caller
        }
    
        return ticket;
    }

    public void deleteTicket(Long selectedTicketID) {
        try (Connection connect = connection.getConnection()) {
            // Execute query
            String query = "DELETE FROM ticket WHERE TICKETID = ?";
            try (PreparedStatement selectStatement = connect.prepareStatement(query)) {
                selectStatement.setLong(1,selectedTicketID);
                selectStatement.executeUpdate();
            } catch (SQLException e) {
            System.out.println("Error fetching ticket: " + e.getMessage());
        }
    
    }catch (SQLException e) {
        System.out.println("Error fetching ticket: " + e.getMessage()); // Re-throw the exception to be handled by the caller
    }
    }
}
