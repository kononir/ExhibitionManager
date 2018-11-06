package bsuir.vlad.model;

public class ExhibitionHall {
    private String name;
    private double square;
    private Address address;
    private String phoneNumber;

    private String ownerName;

    public ExhibitionHall(String name, double square, Address address, String phoneNumber, String ownerName) {
        this.name = name;
        this.square = square;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.ownerName = ownerName;
    }

    public ExhibitionHall() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSquare() {
        return square;
    }

    public void setSquare(double square) {
        this.square = square;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddressStreet() {
        return address.getStreet();
    }

    public void setAddressStreet(String street) {
        address.setStreet(street);
    }

    public String getAddressBuildingNumber() {
        return address.getBuildingNumber();
    }

    public void setAddressBuildingNumber(String buildingNumber) {
        address.setBuildingNumber(buildingNumber);
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
}
