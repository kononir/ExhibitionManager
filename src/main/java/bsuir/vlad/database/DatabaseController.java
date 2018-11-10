package bsuir.vlad.database;

import bsuir.vlad.model.ExhibitionHall;

import java.util.concurrent.Exchanger;

public class DatabaseController {
    private Database database;

    public DatabaseController() {
        database = new Database();
    }

    public void controlSelectingExhibitionHalls(Exchanger<ExhibitionHall> exchanger) {
        database.selectAllExhibitionHalls(exchanger);
    }

    public void controlInsertingExhibitionHall(ExhibitionHall exhibitionHall) {
        database.insertExhibitionHall(exhibitionHall);
    }

    public void controlDeletingExhibitionHall(ExhibitionHall exhibitionHall) {
        database.deleteExhibitionHall(exhibitionHall);
    }

    public void controlUpdatingExhibitionHall(
            String updatingColumnName,
            int updatingRecordIndex,
            ExhibitionHall exhibitionHall
    ) {
        database.updateExhibitionHall(updatingColumnName, updatingRecordIndex, exhibitionHall);
    }
}
