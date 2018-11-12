package bsuir.vlad.model;

public class Owner {
    private String name;
    private Address address;
    private String phoneNumber;

    public Owner(String name, Address address, String phoneNumber) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
