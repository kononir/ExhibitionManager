package bsuir.vlad.usingintable;

import bsuir.vlad.model.Address;

public class ExhibitionInformation {
    private String name;
    private Address address;

    public ExhibitionInformation(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getAddressStreet() {
        return address.getStreet();
    }

    public String getAddressBuildingNumber() {
        return address.getBuildingNumber();
    }
}
