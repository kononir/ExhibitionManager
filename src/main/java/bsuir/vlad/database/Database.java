package bsuir.vlad.database;

import bsuir.vlad.model.ExhibitionHall;
import bsuir.vlad.model.Address;

import java.sql.*;
import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.mysql.cj.jdbc.Driver;

class Database {
    private Connection connection;

    Database() {
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

    void selectAllExhibitionHalls(Exchanger<ExhibitionHall> exchanger) {
        new Thread(() -> {
            try {
                connect();

                Statement statement = connection.createStatement();

                ResultSet resultSet = statement.executeQuery("SELECT * FROM exhibitionhall");

                int timeout = 10;

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

                    exchanger.exchange(exhibitionHall, timeout, TimeUnit.MILLISECONDS);
                }

                exchanger.exchange(null, timeout, TimeUnit.MILLISECONDS);
            } catch (SQLException e) {
                System.out.println("Connection problems!");
                e.printStackTrace();
            } catch (InterruptedException | TimeoutException e) {
                e.printStackTrace();
            } finally {
                disconnect();

                Thread.currentThread().interrupt();
            }
        }).start();
    }

    void insertExhibitionHall(ExhibitionHall exhibitionHall) {
        new Thread(() -> {
            try {
                connect();

                String sqlQuery = "INSERT INTO exhibitionhall " +
                        "(name, square, street, buildingNumber, phoneNumber, ownerName) values(?, ?, ?, ?, ?, ?)";

                PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);

                int nameIndex = 1;
                int squareIndex = 2;
                int streetIndex = 3;
                int buildingNumberIndex = 4;
                int phoneNumberIndex = 5;
                int ownerNameIndex = 6;

                preparedStatement.setString(nameIndex, exhibitionHall.getName());
                preparedStatement.setDouble(squareIndex, exhibitionHall.getSquare());
                preparedStatement.setString(streetIndex, exhibitionHall.getAddressStreet());
                preparedStatement.setString(buildingNumberIndex, exhibitionHall.getAddressBuildingNumber());
                preparedStatement.setString(phoneNumberIndex, exhibitionHall.getPhoneNumber());
                preparedStatement.setString(ownerNameIndex, exhibitionHall.getOwnerName());

                preparedStatement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                disconnect();

                Thread.currentThread().interrupt();
            }
        }).start();
    }

    void deleteExhibitionHall(ExhibitionHall exhibitionHall) {
        new Thread(() -> {
            try {
                connect();

                String sqlQuery = "DELETE FROM exhibitionhall WHERE (name = ? && square = ? && street = ? " +
                        "&& buildingNumber = ? && phoneNumber = ? && ownerName = ?)";

                PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);

                int nameIndex = 1;
                int squareIndex = 2;
                int streetIndex = 3;
                int buildingNumberIndex = 4;
                int phoneNumberIndex = 5;
                int ownerNameIndex = 6;

                preparedStatement.setString(nameIndex, exhibitionHall.getName());
                preparedStatement.setDouble(squareIndex, exhibitionHall.getSquare());
                preparedStatement.setString(streetIndex, exhibitionHall.getAddressStreet());
                preparedStatement.setString(buildingNumberIndex, exhibitionHall.getAddressBuildingNumber());
                preparedStatement.setString(phoneNumberIndex, exhibitionHall.getPhoneNumber());
                preparedStatement.setString(ownerNameIndex, exhibitionHall.getOwnerName());

                preparedStatement.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                disconnect();

                Thread.currentThread().interrupt();
            }
        }).start();
    }

    void updateExhibitionHall(String updatingColumnName, ExhibitionHall exhibitionHall) {
        new Thread(() -> {
            try {
                connect();

                String sqlQuery;

                switch (updatingColumnName) {
                    case "name":
                        sqlQuery = "UPDATE exhibitionhall SET name = ? WHERE (square = ? && street = ? " +
                                "&& buildingNumber = ? && phoneNumber = ? && ownerName = ?)";
                        break;
                    case "square":
                        sqlQuery = "UPDATE exhibitionhall SET square = ? WHERE (name = ? && street = ? " +
                                "&& buildingNumber = ? && phoneNumber = ? && ownerName = ?)";
                        break;
                    case "street":
                        sqlQuery = "UPDATE exhibitionhall SET street = ? WHERE (name = ? && square = ? " +
                                "&& buildingNumber = ? && phoneNumber = ? && ownerName = ?)";
                        break;
                    case "buildingNumber":
                        sqlQuery = "UPDATE exhibitionhall SET buildingNumber = ? WHERE (name = ? && square = ? " +
                                "&& street = ? && phoneNumber = ? && ownerName = ?)";
                        break;
                    case "phoneNumber":
                        sqlQuery = "UPDATE exhibitionhall SET buildingNumber = ? WHERE (name = ? && square = ? " +
                                "&& street = ? && buildingNumber = ? && ownerName = ?)";
                        break;
                    case "ownerName":
                        sqlQuery = "UPDATE exhibitionhall SET ownerName = ? WHERE (name = ? && square = ? " +
                                "&& street = ? && buildingNumber = ? && phoneNumber = ?)";
                        break;
                    default:
                        throw new IllegalArgumentException("Wrong name of updating column!");
                }

                PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);


            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                disconnect();

                Thread.currentThread().interrupt();
            }
        }).start();
    }
}
