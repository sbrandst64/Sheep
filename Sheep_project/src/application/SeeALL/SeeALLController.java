package application.SeeALL;

import application.Model.Tiere;
import application.ModifyTables.ModifySheep;
import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

public class SeeALLController {
    public TableColumn<Tiere, String> schafBezTable;
    public TableColumn<Tiere, String> standortTable;
    public TableColumn<Tiere, String> sheeptypTable;
    public TableColumn<Tiere, String> gebDateTable;
    public TableColumn<Tiere, String> farbeTable;
    public TableColumn<Tiere, String> mutterTable;
    public TableColumn<Tiere, String> vaterTable;
    public TableColumn<Tiere, String> infoTable;
    public MenuItem edit;
    public MenuItem delete;
    public javafx.scene.control.TableView<Tiere> seeALLTableView;

    public void initialize() {
        schafBezTable.setCellValueFactory(new PropertyValueFactory<>("bez"));
        schafBezTable.setCellFactory(TextFieldTableCell.forTableColumn());

        standortTable.setCellValueFactory(new PropertyValueFactory<>("weide"));
        standortTable.setCellFactory(TextFieldTableCell.forTableColumn());

        sheeptypTable.setCellValueFactory(new PropertyValueFactory<>("schaftyp"));
        sheeptypTable.setCellFactory(TextFieldTableCell.forTableColumn());

        gebDateTable.setCellValueFactory(new PropertyValueFactory<>("geb_Date"));
        gebDateTable.setCellFactory(TextFieldTableCell.forTableColumn());

        farbeTable.setCellValueFactory(new PropertyValueFactory<>("color"));
        farbeTable.setCellFactory(TextFieldTableCell.forTableColumn());

        mutterTable.setCellValueFactory(new PropertyValueFactory<>("mutter"));
        mutterTable.setCellFactory(TextFieldTableCell.forTableColumn());

        vaterTable.setCellValueFactory(new PropertyValueFactory<>("vater"));
        vaterTable.setCellFactory(TextFieldTableCell.forTableColumn());

        infoTable.setCellValueFactory(new PropertyValueFactory<>("infos"));
        infoTable.setCellFactory(TextFieldTableCell.forTableColumn());

        refresh();

    }

    public void refresh() {
        seeALLTableView.setItems(Tiere.loadAll());

    }

    //Bearbeitungsmodus aktivieren
    public void edit_Clicked(ActionEvent actionEvent) {
        seeALLTableView.setEditable(true);

    }

    //Änderung bestätigen

    public void edit_Commit(TableColumn.CellEditEvent<Tiere, String> tierecellEditEvent) {
        ModifySheep.editSheep(seeALLTableView.getSelectionModel().getSelectedItem().getBez(),   //Bezeichnung
                seeALLTableView.getSelectionModel().getSelectedItem().getSchaftyp(),            //Schaftyp
                tierecellEditEvent.getTablePosition().getTableColumn().getText(),               //Auswahl
                tierecellEditEvent.getOldValue(),                                               //Alter Wert
                tierecellEditEvent.getNewValue());                                              //neuer Wert
        refresh();
    }

    //Zum löschen übergeben
    public void delete_Clicked(ActionEvent actionEvent) {
        ModifySheep.deleteSheep(seeALLTableView.getSelectionModel().getSelectedItem().getBez(), //Bezeichnung
                seeALLTableView.getSelectionModel().getSelectedItem().getColor(),               //Farbe
                seeALLTableView.getSelectionModel().getSelectedItem().getSchaftyp());           //Schaftyp

        refresh();
    }
}
