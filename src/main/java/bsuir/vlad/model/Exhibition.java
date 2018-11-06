package bsuir.vlad.model;

import java.util.Date;
import java.util.List;

class Exhibition {
    private String type;
    private String name;
    private Date date;

    private ExhibitionHall exhibitionHall;

    private List<ArtistWork> artistWorks;
}
