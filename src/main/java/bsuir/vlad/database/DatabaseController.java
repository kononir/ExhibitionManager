package bsuir.vlad.database;

import bsuir.vlad.model.ExhibitionHall;

import java.util.List;

public class DatabaseController {
    private Database database;

    public DatabaseController() {
        database = new Database();
    }

    public List<ExhibitionHall> controlSelectingExhibitionHalls() {
        return database.selectAllExhibitionHalls();
    }

    public void controlUpdatingExhibitionHalls() {

    }
}
