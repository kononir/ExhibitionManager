package bsuir.vlad.database;

import bsuir.vlad.model.*;
import bsuir.vlad.usingintable.ArtistWorkInformation;
import bsuir.vlad.usingintable.ExhibitionInformation;

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

    public Exhibition controlSelectingExhibition(String exhibitionName) {
        return database.selectExhibition(exhibitionName);
    }

    public void controlFindingArtistWorkInformation(String exhibitionName, Exchanger<ArtistWorkInformation> exchanger) {
        database.findArtistWorkInformation(exhibitionName, exchanger);
    }

    public void controlSelectingArtists(Exchanger<Artist> exchanger) {
        database.selectAllArtists(exchanger);
    }

    public void controlUpdatingArtist(String updatingColumnName, String updatingRecordName, Artist artist) {
        database.updateArtist(updatingColumnName, updatingRecordName, artist);
    }

    public void controlDeletingArtist(String removableRecordName) {
        database.deleteArtist(removableRecordName);
    }

    public void controlInsertingArtist(Artist artist) {
        database.insertArtist(artist);
    }

    public void controlFindingExhibitionsInformationOfAllDates(Exchanger<ExhibitionInformation> exchanger) {
        database.findExhibitionsInformationOfAllDates(exchanger);
    }

    public void controlSelectingArtistWorks(Exchanger<ArtistWork> exchanger) {
        database.selectAllArtistWorks(exchanger);
    }

    public void controlUpdatingArtistWork(String updatingColumnName, String updatingRecordName, ArtistWork artistWork) {
        database.updateArtistWork(updatingColumnName, updatingRecordName, artistWork);
    }

    public void controlInsertingArtistWork(ArtistWork artistWork) {
        database.insertArtistWork(artistWork);
    }

    public void controlDeletingArtistWork(String removableRecordName) {
        database.deleteArtistWork(removableRecordName);
    }
}
