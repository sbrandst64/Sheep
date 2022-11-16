package application.Laemmer;

import application.Model.Database;
import application.Model.Laemmer;
import application.ModifyTables.ModifySheep;
import javafx.event.ActionEvent;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LaemmerController {
    public Label weight_Label;
    public Label weightPerMonth_Label;
    public Label fatherLabel;
    public Label motherLabel;
    public Label birthType_Label;
    public Label schafBez_Label;
    public Label geb_Date_Label;
    public Label sheep_typ_Label;
    public Label ort_Label;
    public Label farbe_Label;
    public Label information_Label;
    public LineChart weight_Chart;

    public TableView<Laemmer> lammerTable;
    public TableColumn<Laemmer, String> schafBezTable;
    public TableColumn<Laemmer, String> standortTable;
    public TableColumn<Laemmer, String> sheeptypTable;
    public TableColumn<Laemmer, String> gebDateTable;
    public TableColumn<Laemmer, String> farbeTable;
    public TableColumn<Laemmer, String> mutterTable;
    public TableColumn<Laemmer, String> vaterTable;
    public TableColumn<Laemmer, String> infoTable;
    public TableColumn<Laemmer, String> geburtsartTable;
    public TableColumn<Laemmer, String> gewichtTable;
    public TableColumn<Laemmer, String> kgTable;
    public MenuItem edit;
    public MenuItem delete;

    // initialisiere die LämmerTableView
    public void initialize() {
        schafBezTable.setCellValueFactory(new PropertyValueFactory<>("bez"));
        schafBezTable.setCellFactory(TextFieldTableCell.forTableColumn()); // somit kann die Zelle als Textfield geöffnet werden

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
//---------------------------------------------------------------------------------------------Lämmer specific
        gewichtTable.setCellValueFactory(new PropertyValueFactory<>("weight"));
        gewichtTable.setCellFactory(TextFieldTableCell.forTableColumn());

        geburtsartTable.setCellValueFactory(new PropertyValueFactory<>("birthType"));
        geburtsartTable.setCellFactory(TextFieldTableCell.forTableColumn());


        refresh();

    }

    // Lämmer Liste neu laden
    public void refresh() {
        lammerTable.setItems(Laemmer.loadLaemmer());

    }

    // Aktiviere den Bearbeitungsmodus
    public void edit_Clicked(ActionEvent actionEvent) {
        lammerTable.setEditable(true);

    }

    // Übergabe von bez,farbe und Schaftyp zum identifizieren des genauen Schafs, um es anschließend löschen zu können
    public void delete_Clicked(ActionEvent actionEvent) {

        ModifySheep.deleteSheep(lammerTable.getSelectionModel().getSelectedItem().getBez(),
                lammerTable.getSelectionModel().getSelectedItem().getColor(),
                lammerTable.getSelectionModel().getSelectedItem().getSchaftyp());
        refresh();
    }

    // Übergabe von bez,schaftyp,selection, alten + neuen Wert für changelog
    public void edit_Commit(TableColumn.CellEditEvent<Laemmer, String> laemmerStringCellEditEvent) {

        ModifySheep.editSheep(lammerTable.getSelectionModel().getSelectedItem().getBez(),
                lammerTable.getSelectionModel().getSelectedItem().getSchaftyp(),
                laemmerStringCellEditEvent.getTablePosition().getTableColumn().getText(),
                laemmerStringCellEditEvent.getOldValue(),
                laemmerStringCellEditEvent.getNewValue());

        refresh();
    }
}


