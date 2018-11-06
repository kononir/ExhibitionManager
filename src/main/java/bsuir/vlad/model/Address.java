package bsuir.vlad.model;

public class Address {
    private String street;
    private String buildingNumber;

    public Address(String street, String buildingNumber) {
        this.street = street;
        this.buildingNumber = buildingNumber;
    }

    String getStreet() {
        return street;
    }

    void setStreet(String street) {
        this.street = street;
    }

    String getBuildingNumber() {
        return buildingNumber;
    }

    void setBuildingNumber(String buildingNumber) {
        this.buildingNumber = buildingNumber;
    }
}
