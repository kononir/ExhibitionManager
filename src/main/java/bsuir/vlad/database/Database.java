package bsuir.vlad.database;

import bsuir.vlad.model.ExhibitionHall;
import bsuir.vlad.model.Address;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.mysql.cj.jdbc.Driver;

public class Database {
    private Connection connection;

    public Database() {
        try {
            Driver driver = new Driver();
            DriverManager.registerDriver(driver);
        } catch (SQLException e) {
            System.out.println("jdbc driver didn't found!");
            e.printStackTrace();
        }
    }

    private void connect() {
        try {
            final String URL = "jdbc:mysql://localhost:3306/exibitinf" +
                    "?useUnicode=true" +
                    "&useJDBCCompliantTimezoneShift=true" +
                    "&useLegacyDatetimeCode=false" +
                    "&serverTimezone=UTC";
            final String USER = "root";
            final String PASSWORD = "root";
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            if (!connection.isClosed()) {
                System.out.println("Connection is established!");
            }
        } catch (SQLException e) {
            System.out.println("Connection problems!");
            e.printStackTrace();
        }
    }

    private void disconnect() {
        try {
            connection.close();

            if (connection.isClosed()) {
                System.out.println("Connection is closed!");
            }
        } catch (SQLException e) {
            System.out.println("Problems with disconnect!");
            e.printStackTrace();
        }
    }

    public List<ExhibitionHall> selectAllExhibitionHalls() {
        List<ExhibitionHall> exhibitionHalls = new ArrayList<>();

        try {
            /*new Thread(() -> {

            }).start();*/
            connect();

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM exhibitionhall");

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                double square = resultSet.getDouble("square");
                String street = resultSet.getString("street");
                String buildingNumber = resultSet.getString("buildingNumber");
                String phoneNumber = resultSet.getString("phoneNumber");
                String ownerName = resultSet.getString("ownerName");

                Address address = new Address(street, buildingNumber);

                ExhibitionHall exhibitionHall = new ExhibitionHall(
                        name,
                        square,
                        address,
                        phoneNumber,
                        ownerName
                );

                exhibitionHalls.add(exhibitionHall);
            }

            disconnect();

            return exhibitionHalls;
        } catch (SQLException e) {
            System.out.println("Connection problems!");
            e.printStackTrace();
        }

        return exhibitionHalls;
    }
}
