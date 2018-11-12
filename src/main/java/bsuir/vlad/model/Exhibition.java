package bsuir.vlad.model;

import java.time.LocalDate;
import java.util.List;

public class Exhibition {
    private String type;
    private String name;
    private LocalDate date;

    private String exhibitionHallName;

    private List<ArtistWork> artistWorks;

    public Exhibition(String type, String name, LocalDate date, String exhibitionHallName) {
        this.type = type;
        this.name = name;
        this.date = date;
        this.exhibitionHallName = exhibitionHallName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getExhibitionHallName() {
        return exhibitionHallName;
    }

    public void setExhibitionHallName(String exhibitionHallName) {
        this.exhibitionHallName = exhibitionHallName;
    }
}
