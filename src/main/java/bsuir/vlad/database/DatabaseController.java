package bsuir.vlad.database;

import bsuir.vlad.model.Exhibition;
import bsuir.vlad.model.ExhibitionHall;
import bsuir.vlad.model.Owner;

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

    public void controlDeletingExhibitionHall(String removableRecordName) {
        database.deleteExhibitionHall(removableRecordName);
    }

    public void controlUpdatingExhibitionHall(
            String updatingColumnName,
            String updatingRecordName,
            ExhibitionHall exhibitionHall
    ) {
        database.updateExhibitionHall(updatingColumnName, updatingRecordName, exhibitionHall);
    }

    public void controlSelectingExhibitions(Exchanger<Exhibition> exchanger) {
        database.selectAllExhibitions(exchanger);
    }

    public void controlInsertingExhibition(Exhibition exhibition) {
        database.insertExhibition(exhibition);
    }

    public void controlDeletingExhibition(String removableRecordName) {
        database.deleteExhibition(removableRecordName);
    }

    public void controlUpdatingExhibition(
            String updatingColumnName,
            String updatingRecordName,
            Exhibition exhibition
    ) {
        database.updateExhibition(updatingColumnName, updatingRecordName, exhibition);
    }

    public void controlSelectingOwners(Exchanger<Owner> exchanger) {
        database.selectAllOwners(exchanger);
    }

    public void controlUpdatingOwner(String updatingColumnName, String updatingRecordName, Owner owner) {
        database.updateOwner(updatingColumnName, updatingRecordName, owner);
    }

    public void controlDeletingOwner(String removableRecordName) {
        database.deleteOwner(removableRecordName);
    }

    public void controlInsertingOwner(Owner owner) {
        database.insertOwner(owner);
    }
}
