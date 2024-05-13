package hellofx.Class;

import java.sql.*;
import java.util.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import hellofx.Class.Person;
import hellofx.FirstApp.Services.MainService;
import javafx.scene.control.Alert.AlertType;

public class DataClass {
    private List<Person> importSQLList;
    private List<Person> exportSQLList;
    private MySQLConnection connection = new MySQLConnection();
    private List<Person> importListCitizens;
    private List<Person> exportListCitizens;
    private MainService Service = new MainService();

    public DataClass() {
        String filePath = "Data/CitizensFile.txt";
        this.importListCitizens = new ArrayList<Person>();
        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split("#");
                try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate date = LocalDate.parse(data[4], formatter);                    
                Person C = new Person(data[0], data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3]), date);
                    this.importListCitizens.add(C);
                    System.out.println(importListCitizens);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Person> getImportList() {
        return importListCitizens;
    }

    public List<Person> getExportList() {
        return exportListCitizens;
    }

    public void setExportList(List<Person> exportList) {
        this.exportListCitizens.addAll(exportList);
        for (Person p : this.exportListCitizens)
            System.out.println(p);
    }

    public List<Person> getSQList(){

            this.importSQLList = new ArrayList<Person>();
            try{

            Connection connect = connection.getConnection();

            Statement statement = connect.createStatement();
            
            // Execute query
            ResultSet resultSet = statement.executeQuery("SELECT * FROM person");
            
            // Process results
            while (resultSet.next()) {
                // Retrieve data
                int id = resultSet.getInt("ID");
                int license = resultSet.getInt("LicenseNumber");
                String name = resultSet.getString("First_Name");
                String LastName = resultSet.getString("Last_Name");
                Date x = resultSet.getDate("LicenseDate");
                LocalDate localDate = x.toLocalDate();
                Person newPerson = new Person(name,LastName,license,id,localDate);
                System.out.println(newPerson);
                this.importSQLList.add(newPerson);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return this.importSQLList;
    }
    public void exportSQLList() throws IOException{
        String filePath = "Data/CitizensFile.txt";
        BufferedWriter deleter = new BufferedWriter(new FileWriter(filePath));
        for (Person person : this.getSQList()) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath,true))) {
                    if (!this.importListCitizens.contains(person)) {
                        writer.write(person.toString());
                        writer.flush();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        this.Service.Alert("Success", "Successfuly Exported All tickets !", AlertType.CONFIRMATION);                    

    }

    public int getTicketOwner(Long ticketID){
        try{

        Connection connect = connection.getConnection();        
        String query = ("SELECT P.ID FROM person P, ticket T WHERE P.LicenseNumber = T.LicenseNumber AND T.ticketID = ?"); 
        PreparedStatement selectStatement = connect.prepareStatement(query);
        selectStatement.setLong(1,ticketID);
        ResultSet rs = selectStatement.executeQuery();
        if (rs.next()) {
            return rs.getInt("ID");
        }
        else{
            return 0;
        }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


}
