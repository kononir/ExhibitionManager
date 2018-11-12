package bsuir.vlad.database;

import bsuir.vlad.model.Exhibition;
import bsuir.vlad.model.ExhibitionHall;
import bsuir.vlad.model.Address;

import java.sql.*;
import java.time.LocalDate;
import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import bsuir.vlad.model.Owner;
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

                ResultSet resultSet = statement.executeQuery("SELECT * FROM exhibitionhall;");

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
                        "(name, square, street, buildingNumber, phoneNumber, ownerName) values(?, ?, ?, ?, ?, ?);";

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

    void deleteExhibitionHall(String removableRecordName) {
        new Thread(() -> {
            try {
                connect();

                String sqlQuery = "DELETE FROM exhibitionhall WHERE (name = ?);";

                PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);

                int nameIndex = 1;

                preparedStatement.setString(nameIndex, removableRecordName);

                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                disconnect();

                Thread.currentThread().interrupt();
            }
        }).start();
    }

    void updateExhibitionHall(String updatingColumnName, String updatingRecordName, ExhibitionHall exhibitionHall) {
        new Thread(() -> {
            try {
                connect();

                String sqlQuery = "UPDATE exhibitionhall SET " + updatingColumnName
                        + " = ? WHERE name = ?;";

                PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);

                int columnIndex = 1;
                int nameIndex = 2;

                switch (updatingColumnName) {
                    case "name":
                        preparedStatement.setString(columnIndex, exhibitionHall.getName());
                        break;
                    case "square":
                        preparedStatement.setDouble(columnIndex, exhibitionHall.getSquare());
                        break;
                    case "street":
                        preparedStatement.setString(columnIndex, exhibitionHall.getAddressStreet());
                        break;
                    case "buildingNumber":
                        preparedStatement.setString(columnIndex, exhibitionHall.getAddressBuildingNumber());
                        break;
                    case "phoneNumber":
                        preparedStatement.setString(columnIndex, exhibitionHall.getPhoneNumber());
                        break;
                    case "ownerName":
                        preparedStatement.setString(columnIndex, exhibitionHall.getOwnerName());
                        break;
                    default:
                        throw new IllegalArgumentException("Is no such column name in table!");
                }

                preparedStatement.setString(nameIndex, updatingRecordName);

                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                disconnect();

                Thread.currentThread().interrupt();
            }
        }).start();
    }

    void selectAllExhibitions(Exchanger<Exhibition> exchanger) {
        new Thread(() -> {
            try {
                connect();

                Statement statement = connection.createStatement();

                ResultSet resultSet = statement.executeQuery("SELECT * FROM exhibition;");

                int timeout = 10;

                while (resultSet.next()) {
                    String type = resultSet.getString("type");
                    String name = resultSet.getString("name");
                    LocalDate date = resultSet.getDate("date").toLocalDate();
                    String exhibitionHallName = resultSet.getString("exhibitionHallName");

                    Exhibition exhibition = new Exhibition(
                            type,
                            name,
                            date,
                            exhibitionHallName
                    );

                    exchanger.exchange(exhibition, timeout, TimeUnit.MILLISECONDS);
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

    void insertExhibition(Exhibition exhibition) {
        new Thread(() -> {
            try {
                connect();

                String sqlQuery = "INSERT INTO exhibition " +
                        "(type, name, date, exhibitionHallName) values(?, ?, ?, ?);";

                PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);

                int typeIndex = 1;
                int nameIndex = 2;
                int dateIndex = 3;
                int exhibitionHallNameIndex = 4;

                preparedStatement.setString(typeIndex, exhibition.getType());
                preparedStatement.setString(nameIndex, exhibition.getName());
                preparedStatement.setDate(dateIndex, java.sql.Date.valueOf(exhibition.getDate()));
                preparedStatement.setString(exhibitionHallNameIndex, exhibition.getExhibitionHallName());

                preparedStatement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                disconnect();

                Thread.currentThread().interrupt();
            }
        }).start();
    }

    void deleteExhibition(String removableRecordName) {
        new Thread(() -> {
            try {
                connect();

                String sqlQuery = "DELETE FROM exhibition WHERE (name = ?);";

                PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);

                int nameIndex = 1;

                preparedStatement.setString(nameIndex, removableRecordName);

                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                disconnect();

                Thread.currentThread().interrupt();
            }
        }).start();
    }

    void updateExhibition(String updatingColumnName, String updatingRecordName, Exhibition exhibition) {
        new Thread(() -> {
            try {
                connect();

                String sqlQuery = "UPDATE exhibition SET " + updatingColumnName
                        + " = ? WHERE name = ?;";

                PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);

                int columnIndex = 1;
                int nameIndex = 2;

                switch (updatingColumnName) {
                    case "type":
                        preparedStatement.setString(columnIndex, exhibition.getType());
                        break;
                    case "name":
                        preparedStatement.setString(columnIndex, exhibition.getName());
                        break;
                    case "date":
                        preparedStatement.setDate(columnIndex, java.sql.Date.valueOf(exhibition.getDate().toString()));
                        break;
                    case "exhibitionHallName":
                        preparedStatement.setString(columnIndex, exhibition.getExhibitionHallName());
                        break;
                    default:
                        throw new IllegalArgumentException("Is no such column name in table!");
                }

                preparedStatement.setString(nameIndex, updatingRecordName);

                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                disconnect();

                Thread.currentThread().interrupt();
            }
        }).start();
    }

    void selectAllOwners(Exchanger<Owner> exchanger) {
        new Thread(() -> {
            try {
                connect();

                Statement statement = connection.createStatement();

                ResultSet resultSet = statement.executeQuery("SELECT * FROM owner;");

                int timeout = 10;

                while (resultSet.next()) {
                    String name = resultSet.getString("name");
                    String addressStreet = resultSet.getString("addressStreet");
                    String addressBuildingNumber = resultSet.getString("addressBuildingNumber");
                    String phoneNumber = resultSet.getString("phoneNumber");

                    Address address = new Address(addressStreet, addressBuildingNumber);

                    Owner owner = new Owner(
                            name,
                            address,
                            phoneNumber
                    );

                    exchanger.exchange(owner, timeout, TimeUnit.MILLISECONDS);
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

    void updateOwner(String updatingColumnName, String updatingRecordName, Owner owner) {
        new Thread(() -> {
            try {
                connect();

                String sqlQuery = "UPDATE owner SET " + updatingColumnName
                        + " = ? WHERE name = ?;";

                PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);

                int columnIndex = 1;
                int nameIndex = 2;

                switch (updatingColumnName) {
                    case "name":
                        preparedStatement.setString(columnIndex, owner.getName());
                        break;
                    case "street":
                        preparedStatement.setString(columnIndex, owner.getAddressStreet());
                        break;
                    case "buildingNumber":
                        preparedStatement.setString(columnIndex, owner.getAddressBuildingNumber());
                        break;
                    case "phoneNumber":
                        preparedStatement.setString(columnIndex, owner.getPhoneNumber());
                        break;
                    default:
                        throw new IllegalArgumentException("Is no such column name in table!");
                }

                preparedStatement.setString(nameIndex, updatingRecordName);

                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                disconnect();

                Thread.currentThread().interrupt();
            }
        }).start();
    }

    void deleteOwner(String removableRecordName) {
        new Thread(() -> {
            try {
                connect();

                String sqlQuery = "DELETE FROM owner WHERE (name = ?);";

                PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);

                int nameIndex = 1;

                preparedStatement.setString(nameIndex, removableRecordName);

                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                disconnect();

                Thread.currentThread().interrupt();
            }
        }).start();
    }

    void insertOwner(Owner owner) {
        new Thread(() -> {
            try {
                connect();

                String sqlQuery = "INSERT INTO owner " +
                        "(name, street, buildingNumber, phoneNumber) values(?, ?, ?, ?);";

                PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);

                int nameIndex = 1;
                int streetIndex = 2;
                int buildingNumberIndex = 3;
                int phoneNumberIndex = 4;

                preparedStatement.setString(nameIndex, owner.getName());
                preparedStatement.setString(streetIndex, owner.getAddressStreet());
                preparedStatement.setString(buildingNumberIndex, owner.getAddressBuildingNumber());
                preparedStatement.setString(phoneNumberIndex, owner.getPhoneNumber());

                preparedStatement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                disconnect();

                Thread.currentThread().interrupt();
            }
        }).start();
    }
}
