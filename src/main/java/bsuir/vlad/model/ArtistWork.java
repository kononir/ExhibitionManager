package bsuir.vlad.model;

import java.time.LocalDate;
import java.util.Date;

public class ArtistWork {
    private String name;
    private String type;

    private LocalDate creationDate;

    private double width;
    private double height;
    private double volume;

    private String artistLastName;
    private String exhibitionName;

    public ArtistWork(
            String name,
            String type,
            LocalDate creationDate,
            double width,
            double height,
            double volume,
            String artistLastName,
            String exhibitionName
    ) {
        this.name = name;
        this.type = type;
        this.creationDate = creationDate;
        this.width = width;
        this.height = height;
        this.volume = volume;
        this.artistLastName = artistLastName;
        this.exhibitionName = exhibitionName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public String getArtistLastName() {
        return artistLastName;
    }

    public void setArtistLastName(String artistLastName) {
        this.artistLastName = artistLastName;
    }

    public String getExhibitionName() {
        return exhibitionName;
    }

    public void setExhibitionName(String exhibitionName) {
        this.exhibitionName = exhibitionName;
    }
}
