package bsuir.vlad.database;

import bsuir.vlad.model.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import bsuir.vlad.usingintable.ArtistWorkInformation;
import bsuir.vlad.usingintable.ExhibitionInformation;
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
                System.out.println("Connection problems!");
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
                System.out.println("Connection problems!");
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
                System.out.println("Connection problems!");
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
                preparedStatement.setDate(dateIndex, Date.valueOf(exhibition.getDate().plusDays(1)));
                preparedStatement.setString(exhibitionHallNameIndex, exhibition.getExhibitionHallName());

                preparedStatement.execute();
            } catch (SQLException e) {
                System.out.println("Connection problems!");
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
                System.out.println("Connection problems!");
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
                        preparedStatement.setDate(columnIndex, java.sql.Date.valueOf(exhibition.getDate()));
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
                System.out.println("Connection problems!");
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
                    String addressStreet = resultSet.getString("street");
                    String addressBuildingNumber = resultSet.getString("buildingNumber");
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
                System.out.println("Connection problems!");
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
                System.out.println("Connection problems!");
                e.printStackTrace();
            } finally {
                disconnect();

                Thread.currentThread().interrupt();
            }
        }).start();
    }

    Exhibition selectExhibition(String exhibitionName) {
        Exhibition exhibition = null;

        try {
            connect();

            String sqlQuery = "SELECT * FROM exhibition WHERE name = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);

            int nameIndex = 1;
            preparedStatement.setString(nameIndex, exhibitionName);

            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();
            String type = resultSet.getString("type");
            String name = resultSet.getString("name");
            LocalDate date = resultSet.getDate("date").toLocalDate();
            String exhibitionHallName = resultSet.getString("exhibitionHallName");

            exhibition = new Exhibition(
                    type,
                    name,
                    date,
                    exhibitionHallName
            );
        } catch (SQLException e) {
            System.out.println("Connection problems!");
            e.printStackTrace();
        } finally {
            disconnect();
        }

        return exhibition;
    }

    void findArtistWorkInformation(String exhibitionName, Exchanger<ArtistWorkInformation> exchanger) {
        new Thread(() -> {
            try {
                connect();

                String sqlQuery =
                        "SELECT artistWork.name, " +
                                "artistWork.type, " +
                                "artist.firstName, " +
                                "artist.lastName, " +
                                "artist.patronymic, " +
                                "TIMESTAMPDIFF(YEAR, artist.birthdayDate, CURDATE()) AS age, " +
                                "artistwork.creationDate " +
                        "FROM artistwork " +
                        "INNER JOIN artist ON(artistwork.artistLastName = artist.lastName) " +
                        "WHERE artistwork.exhebitionName = ?;";

                PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);

                int nameIndex = 1;
                preparedStatement.setString(nameIndex, exhibitionName);

                ResultSet resultSet = preparedStatement.executeQuery();

                int timeout = 20;

                while (resultSet.next()) {
                    String artistWorkName = resultSet.getString("artistWork.name");
                    String artistWorkType = resultSet.getString("artistWork.type");
                    String artistFirstName = resultSet.getString("artist.firstName");
                    String artistLastName = resultSet.getString("artist.lastName");
                    String artistPatronymic = resultSet.getString("artist.patronymic");

                    int ageIndex = 6;
                    int artistAge = resultSet.getInt(ageIndex);

                    LocalDate artistWorkCreationDate = resultSet.getDate("artistWork.creationDate").toLocalDate();

                    ArtistWorkInformation information = new ArtistWorkInformation(
                            artistWorkName,
                            artistWorkType,
                            artistFirstName,
                            artistLastName,
                            artistPatronymic,
                            artistAge,
                            artistWorkCreationDate
                    );

                    exchanger.exchange(information, timeout, TimeUnit.MILLISECONDS);
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

    void selectAllArtists(Exchanger<Artist> exchanger) {
        new Thread(() -> {
            try {
                connect();

                Statement statement = connection.createStatement();

                ResultSet resultSet = statement.executeQuery("SELECT * FROM artist;");

                int timeout = 10;

                while (resultSet.next()) {
                    String firstName = resultSet.getString("firstName");
                    String lastName = resultSet.getString("lastName");
                    String patronymic = resultSet.getString("patronymic");
                    String birthdayPlace = resultSet.getString("birthdayPlace");
                    LocalDate birthdayDate = resultSet.getDate("birthdayDate").toLocalDate();
                    String vitae = resultSet.getString("vitae");
                    String education = resultSet.getString("education");

                    Artist artist = new Artist(
                            firstName,
                            lastName,
                            patronymic,
                            birthdayPlace,
                            birthdayDate,
                            vitae,
                            education
                    );

                    exchanger.exchange(artist, timeout, TimeUnit.MILLISECONDS);
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

    void updateArtist(String updatingColumnName, String updatingRecordName, Artist artist) {
        new Thread(() -> {
            try {
                connect();

                String sqlQuery = "UPDATE artist SET " + updatingColumnName
                        + " = ? WHERE lastName = ?;";

                PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);

                int columnIndex = 1;
                int nameIndex = 2;

                switch (updatingColumnName) {
                    case "firstName":
                        preparedStatement.setString(columnIndex, artist.getFirstName());
                        break;
                    case "lastName":
                        preparedStatement.setString(columnIndex, artist.getLastName());
                        break;
                    case "patronymic":
                        preparedStatement.setString(columnIndex, artist.getPatronymic());
                        break;
                    case "birthdayPlace":
                        preparedStatement.setString(columnIndex, artist.getBirthdayPlace());
                        break;
                    case "birthdayDate":
                        preparedStatement.setDate(columnIndex, java.sql.Date.valueOf(artist.getBirthdayDate()));
                        break;
                    case "vitae":
                        preparedStatement.setString(columnIndex, artist.getVitae());
                        break;
                    case "education":
                        preparedStatement.setString(columnIndex, artist.getEducation());
                        break;
                    default:
                        throw new IllegalArgumentException("Is no such column name in table!");
                }

                preparedStatement.setString(nameIndex, updatingRecordName);

                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Connection problems!");
                e.printStackTrace();
            } finally {
                disconnect();

                Thread.currentThread().interrupt();
            }
        }).start();
    }

    void deleteArtist(String removableRecordName) {
        new Thread(() -> {
            try {
                connect();

                String sqlQuery = "DELETE FROM artist WHERE (lastName = ?);";

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

    void insertArtist(Artist artist) {
        new Thread(() -> {
            try {
                connect();

                String sqlQuery = "INSERT INTO artist " +
                        "(firstName, lastName, patronymic, birthdayPlace, birthdayDate, vitae, education) " +
                        "values(?, ?, ?, ?, ?, ?, ?);";

                PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);

                int firstNameIndex = 1;
                int secondNameIndex = 2;
                int patronymicIndex = 3;
                int birthdayPlaceIndex = 4;
                int birthdayDateIndex = 5;
                int vitaeIndex = 6;
                int educationIndex = 7;

                preparedStatement.setString(firstNameIndex, artist.getFirstName());
                preparedStatement.setString(secondNameIndex, artist.getLastName());
                preparedStatement.setString(patronymicIndex, artist.getPatronymic());
                preparedStatement.setString(birthdayPlaceIndex, artist.getBirthdayPlace());
                preparedStatement.setDate(birthdayDateIndex, Date.valueOf(artist.getBirthdayDate().plusDays(1)));
                preparedStatement.setString(vitaeIndex, artist.getVitae());
                preparedStatement.setString(educationIndex, artist.getEducation());

                preparedStatement.execute();
            } catch (SQLException e) {
                System.out.println("Connection problems!");
                e.printStackTrace();
            } finally {
                disconnect();

                Thread.currentThread().interrupt();
            }
        }).start();
    }

    void findExhibitionsInformationOfAllDates(Exchanger<ExhibitionInformation> exchanger) {
        new Thread(() -> {
            try {
                connect();

                String sqlQuery =
                        "SELECT exhibition.name, exhibitionhall.street, exhibitionhall.buildingNumber " +
                                "FROM exhibition " +
                                "INNER JOIN exhibitionhall " +
                                "ON (exhibition.exhibitionHallName = exhibitionhall.name) " +
                                "WHERE date = CURDATE();";

                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sqlQuery);

                int timeout = 10;

                while (resultSet.next()) {
                    String exhibitionName = resultSet.getString("exhibition.name");
                    String exhibitionHallStreet = resultSet.getString("exhibitionhall.street");
                    String exhibitionHallBuildingNumber = resultSet.getString("exhibitionhall.buildingNumber");

                    Address exhibitionHallAddress = new Address(exhibitionHallStreet, exhibitionHallBuildingNumber);

                    ExhibitionInformation exhibitionInformation = new ExhibitionInformation(
                            exhibitionName, exhibitionHallAddress
                    );

                    exchanger.exchange(exhibitionInformation, timeout, TimeUnit.MILLISECONDS);
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

    void selectAllArtistWorks(Exchanger<ArtistWork> exchanger) {
        new Thread(() -> {
            try {
                connect();

                Statement statement = connection.createStatement();

                ResultSet resultSet = statement.executeQuery("SELECT * FROM artistwork;");

                int timeout = 10;

                while (resultSet.next()) {
                    String name = resultSet.getString("name");
                    String type = resultSet.getString("type");
                    LocalDate creationDate = resultSet.getDate("creationDate").toLocalDate();
                    Double width = resultSet.getDouble("width");
                    Double height = resultSet.getDouble("height");
                    Double volume = resultSet.getDouble("volume");
                    String artistLastName = resultSet.getString("artistLastName");
                    String exhebitionName = resultSet.getString("exhebitionName");

                    ArtistWork artistWork = new ArtistWork(
                            name,
                            type,
                            creationDate,
                            width,
                            height,
                            volume,
                            artistLastName,
                            exhebitionName
                    );

                    exchanger.exchange(artistWork, timeout, TimeUnit.MILLISECONDS);
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

    void updateArtistWork(String updatingColumnName, String updatingRecordName, ArtistWork artistWork) {
        new Thread(() -> {
            try {
                connect();

                String sqlQuery = "UPDATE artistwork SET " + updatingColumnName
                        + " = ? WHERE name = ?;";

                PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);

                int columnIndex = 1;
                int nameIndex = 2;

                switch (updatingColumnName) {
                    case "name":
                        preparedStatement.setString(columnIndex, artistWork.getName());
                        break;
                    case "type":
                        preparedStatement.setString(columnIndex, artistWork.getType());
                        break;
                    case "creationDate":
                        preparedStatement.setDate(columnIndex, Date.valueOf(artistWork.getCreationDate()));
                        break;
                    case "width":
                        preparedStatement.setDouble(columnIndex, artistWork.getWidth());
                        break;
                    case "height":
                        preparedStatement.setDouble(columnIndex, artistWork.getHeight());
                        break;
                    case "volume":
                        preparedStatement.setDouble(columnIndex, artistWork.getVolume());
                        break;
                    case "artistLastName":
                        preparedStatement.setString(columnIndex, artistWork.getArtistLastName());
                        break;
                    case "exhibitionName":
                        preparedStatement.setString(columnIndex, artistWork.getExhibitionName());
                        break;
                    default:
                        throw new IllegalArgumentException("Is no such column name in table!");
                }

                preparedStatement.setString(nameIndex, updatingRecordName);

                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Connection problems!");
                e.printStackTrace();
            } finally {
                disconnect();

                Thread.currentThread().interrupt();
            }
        }).start();
    }

    void insertArtistWork(ArtistWork artistWork) {
        new Thread(() -> {
            try {
                connect();

                String sqlQuery = "INSERT INTO artistwork " +
                        "(name, type, creationDate, width, height, volume, artistLastName, exhebitionName) " +
                        "values(?, ?, ?, ?, ?, ?, ?, ?);";

                PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);

                int nameIndex = 1;
                int typeIndex = 2;
                int creationDateIndex = 3;
                int widthIndex = 4;
                int heightIndex = 5;
                int volumeIndex = 6;
                int artistLastNameIndex = 7;
                int exhebitionNameIndex = 8;

                preparedStatement.setString(nameIndex, artistWork.getName());
                preparedStatement.setString(typeIndex, artistWork.getType());
                preparedStatement.setDate(creationDateIndex, Date.valueOf(artistWork.getCreationDate().plusDays(1)));
                preparedStatement.setDouble(widthIndex, artistWork.getWidth());
                preparedStatement.setDouble(heightIndex, artistWork.getHeight());
                preparedStatement.setDouble(volumeIndex, artistWork.getVolume());
                preparedStatement.setString(artistLastNameIndex, artistWork.getArtistLastName());
                preparedStatement.setString(exhebitionNameIndex, artistWork.getExhibitionName());

                preparedStatement.execute();
            } catch (SQLException e) {
                System.out.println("Connection problems!");
                e.printStackTrace();
            } finally {
                disconnect();

                Thread.currentThread().interrupt();
            }
        }).start();
    }

    void deleteArtistWork(String removableRecordName) {
        new Thread(() -> {
            try {
                connect();

                String sqlQuery = "DELETE FROM artistwork WHERE (name = ?);";

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
}
