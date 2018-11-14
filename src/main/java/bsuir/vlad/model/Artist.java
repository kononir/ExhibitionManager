package bsuir.vlad.model;

import java.time.LocalDate;

public class Artist {
    private String firstName;
    private String lastName;
    private String patronymic;

    private String birthdayPlace;
    private LocalDate birthdayDate;

    private String vitae;
    private String education;

    public Artist(
            String firstName,
            String lastName,
            String patronymic,
            String birthdayPlace,
            LocalDate birthdayDate,
            String vitae,
            String education
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.birthdayPlace = birthdayPlace;
        this.birthdayDate = birthdayDate;
        this.vitae = vitae;
        this.education = education;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getBirthdayPlace() {
        return birthdayPlace;
    }

    public void setBirthdayPlace(String birthdayPlace) {
        this.birthdayPlace = birthdayPlace;
    }

    public LocalDate getBirthdayDate() {
        return birthdayDate;
    }

    public void setBirthdayDate(LocalDate birthdayDate) {
        this.birthdayDate = birthdayDate;
    }

    public String getVitae() {
        return vitae;
    }

    public void setVitae(String vitae) {
        this.vitae = vitae;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }
}
