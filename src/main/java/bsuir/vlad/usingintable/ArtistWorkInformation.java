package bsuir.vlad.usingintable;

import java.time.LocalDate;

public class ArtistWorkInformation {
    private String artistWorkName;
    private String artistWorkType;
    private String artistFirstName;
    private String artistLastName;
    private String artistPatronymic;
    private LocalDate artistBirthdayDate;
    private LocalDate artistWorkCreationDate;

    public ArtistWorkInformation(
            String artistWorkName,
            String artistWorkType,
            String artistFirstName,
            String artistLastName,
            String artistPatronymic,
            LocalDate artistBirthdayDate,
            LocalDate artistWorkCreationDate
    ) {
        this.artistWorkName = artistWorkName;
        this.artistWorkType = artistWorkType;
        this.artistFirstName = artistFirstName;
        this.artistLastName = artistLastName;
        this.artistPatronymic = artistPatronymic;
        this.artistBirthdayDate = artistBirthdayDate;
        this.artistWorkCreationDate = artistWorkCreationDate;
    }

    public String getArtistWorkName() {
        return artistWorkName;
    }

    public String getArtistWorkType() {
        return artistWorkType;
    }

    public String getArtistFirstName() {
        return artistFirstName;
    }

    public String getArtistLastName() {
        return artistLastName;
    }

    public String getArtistPatronymic() {
        return artistPatronymic;
    }

    public LocalDate getArtistBirthdayDate() {
        return artistBirthdayDate;
    }

    public LocalDate getArtistWorkCreationDate() {
        return artistWorkCreationDate;
    }
}
