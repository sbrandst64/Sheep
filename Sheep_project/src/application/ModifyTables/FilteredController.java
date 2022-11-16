package application.ModifyTables;

import application.Model.Database;
import application.Model.Tiere;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FilteredController {
    public TextField bezFilterTextfield;
    public TextField farbeFilterTextfield;
    public TextField mutterFilterTextfield;
    public TextField vaterFilterTextfield;
    public TextField weideFilterTextfield;
    public TextField schaftypFilterTextfield;
    public TextField gebFilterTextfield;
    public TextField sonstigeInfosFilterTextfield;

    public TableView filteredSheepsTableView;
    public TableColumn schafBezTable;
    public TableColumn standortTable;
    public TableColumn sheeptypTable;
    public TableColumn gebDateTable;
    public TableColumn farbeTable;
    public TableColumn mutterTable;
    public TableColumn vaterTable;
    public TableColumn infoTable;

    //Initialisiere FilterTableView
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

    // aktualisiere FilterTableView
    public void refresh() {
        filteredSheepsTableView.setItems(Tiere.loadAll());
    }

    public void filter_change(KeyEvent keyEvent) {

        filteredSheepsTableView.getItems().clear();
        ObservableList<Tiere> filteredsheeps = FXCollections.observableArrayList();
        filteredsheeps = filteredSheepsTableView.getItems();
        Connection c = Database.getInstance();
        String sql = "SELECT * FROM sbrandst_Tiere WHERE ";


        //SQL Statement anhand des Filters verschieden zusammenbauen
        if (!bezFilterTextfield.getText().equals("")) {
            String bezStatement = "Schaf_bez LIKE '%" + bezFilterTextfield.getText() + "%' AND ";
            sql = sql.concat(bezStatement);
        }
        if (!weideFilterTextfield.getText().equals("")) {
            String weideStatement = "Weide LIKE '%" + weideFilterTextfield.getText() + "%' AND ";
            sql = sql.concat(weideStatement);
        }
        if (!schaftypFilterTextfield.getText().equals("")) {
            String schaftypStatement = "Schaftyp LIKE '%" + schaftypFilterTextfield.getText() + "%' AND ";
            sql = sql.concat(schaftypStatement);
        }
        if (!gebFilterTextfield.getText().equals("")) {
            String gebStatement = "Geb_Datum LIKE '%" + gebFilterTextfield.getText() + "%' AND ";
            sql = sql.concat(gebStatement);
        }
        if (!farbeFilterTextfield.getText().equals("")) {
            String farbeStatement = "Farbe LIKE '%" + farbeFilterTextfield.getText() + "%' AND ";
            sql = sql.concat(farbeStatement);
        }
        if (!mutterFilterTextfield.getText().equals("")) {
            String mutterStatement = "Mutter_bez LIKE '%" + mutterFilterTextfield.getText() + "%' AND ";
            sql = sql.concat(mutterStatement);
        }
        if (!vaterFilterTextfield.getText().equals("")) {
            String vaterStatement = "Vater_bez LIKE '%" + vaterFilterTextfield.getText() + "%' AND ";
            sql = sql.concat(vaterStatement);
        }
        if (!sonstigeInfosFilterTextfield.getText().equals("")) {
            String sonstigesStatement = "Sonstige_Informationen LIKE '%" + sonstigeInfosFilterTextfield.getText() + "%' AND ";
            sql = sql.concat(sonstigesStatement);
        }

        // Wenn kein Filter angewandt wird so wird das Where zum Schluss der Anfrage weggelassen
        if (sql.endsWith("WHERE ")) {
            sql = sql.replace("WHERE ", "");

        } else {
            // Bei Filter: AND zum Schluss Entfernen
            sql = sql.concat("|"); // "|" kennzeichnet ende des Strings, man braucht diesen Indikator, damit wir genau wissen welches das letzte AND im String ist
            sql = sql.replace("AND |", ""); // Letztes AND aus der SQL Anfrage löschen
        }
        System.out.println(sql);

        try {
            PreparedStatement statement = c.prepareStatement(sql);

            Tiere.getFromDatabase(filteredsheeps, statement);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void edit_Commit(TableColumn.CellEditEvent cellEditEvent) {
    }

    public void edit_Clicked(ActionEvent actionEvent) {
    }

    public void delete_Clicked(ActionEvent actionEvent) {
    }
}
