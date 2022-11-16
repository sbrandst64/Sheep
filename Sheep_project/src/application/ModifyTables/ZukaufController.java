package application.ModifyTables;

import application.Controller;
import application.Main;
import application.Model.Database;
import application.Model.Mutterschaf;
import application.Model.Tiere;
import application.Model.Widder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ZukaufController {
    public TextField add_Gewicht;
    public TextField add_geburtsart;
    public TextField add_Schaf_bez;
    public TextField add_Farbe;
    public TextField add_Aufenthaltsort;
    public TextField add_diverse;
    public TabPane sheepType;
    public DatePicker add_Geb_Date;

    public ListView ohrmackerlViewAddSheep;
    public AnchorPane anchorPane;
    public TextField addSheepMutterField;
    public TextField addSheepVaterField;
    public ListView mutterView;
    public ListView vaterView;
    public Button addNewSheep_button;
    public Button cancelButton;
    public Button zukaufButton;
    public Button ablammungButton;
    public DatePicker deckdatumVon;
    public DatePicker deckdatumBis;
    public TextField nameLabel;
    public TextField gebAnzahlLabel;


    public void initialize() {

        ohrmackerlViewAddSheep.setItems(OhrmackerlController.loadOhrmackerl());

        //t1 durchläuft jedes Mutterschaf/Widder und fügt bei jedem durchlaufen, im Listview eintrag, sowohl die bez als auch die Farbe ein (Für bessere Übersicht)
        for (Tiere t1 : Mutterschaf.loadMutter()) {
            mutterView.getItems().add(t1.getBez() + " | " + t1.getColor());
        }
        for (Tiere t1 : Widder.loadWidder()) {
            vaterView.getItems().add(t1.getBez() + " | " + t1.getColor());
        }

    }

    // Hinzufügen des Schafs in Tiere als auch in der dazugehörigen Untertabelle
    public void addButtonClicked(ActionEvent actionEvent) {

        Connection c = Database.getInstance();
        PreparedStatement statement = null;
        try {
            //in Tiere hinzufügen
            statement = c.prepareStatement("INSERT INTO sbrandst_Tiere (Schaf_bez,Geb_Datum,Schaftyp,Sonstige_Informationen,Weide,Farbe,Mutter_bez,Vater_bez) " +
                    "VALUES (?,?,?,?,?,?,?,?)");
            statement.setString(1, add_Schaf_bez.getText());
            statement.setString(2, add_Geb_Date.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            statement.setString(3, sheepType.getSelectionModel().getSelectedItem().getText());
            statement.setString(4, add_diverse.getText());
            statement.setString(5, add_Aufenthaltsort.getText());
            statement.setString(6, add_Farbe.getText());
            statement.setString(7, addSheepMutterField.getText());
            statement.setString(8, addSheepVaterField.getText());

            statement.executeUpdate();

            //in Untertabellen einfügen
            if ((sheepType.getSelectionModel().getSelectedItem().getText().equals("Mutterschaf"))) {
                statement = c.prepareStatement("INSERT INTO sbrandst_Mutterschaf (Mutterschaf_bez,Anzahl_an_Geburten) " +
                        "VALUES (?,?)");
                statement.setString(1, add_Schaf_bez.getText());
                statement.setInt(2, Integer.parseInt(gebAnzahlLabel.getText()));

            } else if ((sheepType.getSelectionModel().getSelectedItem().getText().equals("Lamm"))) {
                statement = c.prepareStatement("INSERT INTO sbrandst_Laemmer (Lamm_bez,Gewicht_in_KG,Geburtsart) " +
                        "VALUES (?,?,?)");
                statement.setString(1, add_Schaf_bez.getText());
                statement.setString(2, add_Gewicht.getText());
                statement.setString(3, add_geburtsart.getText());


            } else if ((sheepType.getSelectionModel().getSelectedItem().getText().equals("Widder"))) {
                statement = c.prepareStatement("INSERT INTO sbrandst_Widder (Widder_bez,Name,Deckdatum_von,Deckdatum_bis) " +
                        "VALUES (?,?,?,?)");
                statement.setString(1, add_Schaf_bez.getText());
                statement.setString(2, nameLabel.getText());
                statement.setString(3, deckdatumVon.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                statement.setString(4, deckdatumBis.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));

            }

            statement.executeUpdate();
            // Ausgewähltes Ohrmackerl aus der verfügbaren Liste löschen
            statement = c.prepareStatement("DELETE FROM sbrandst_Ohrmackerl WHERE Ohrmackerl_Bez = ? ");
            statement.setString(1, String.valueOf(ohrmackerlViewAddSheep.getSelectionModel().getSelectedItem()));

            statement.executeUpdate();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        Main.mainController.addToChangelogAddSheep(add_Schaf_bez.getText(), add_Farbe.getText(), sheepType.getSelectionModel().getSelectedItem().getText());

        //Schließe das Zukauf Fester
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        stage.close();
    }

    //Schließe Zukauf Fenster wenn cancel gedrückt wurde
    public void cancelButtonClicked(ActionEvent actionEvent) {
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        stage.close();

    }

    //Schreibe ins Textfield das ausgewählte Ohrmackerl
    public void selectMackerl(MouseEvent mouseEvent) {
        if (ohrmackerlViewAddSheep.getSelectionModel().getSelectedItem() != null) {

            add_Farbe.setText(ohrmackerlViewAddSheep.getSelectionModel().getSelectedItem().toString());
        }
    }

    //Schreibe ins Vater-Textfield dan ausgewählten Widder ohne Zusatzinfo
    public void vaterViewClicked(MouseEvent mouseEvent) {
        if (vaterView.getSelectionModel().getSelectedItem() != null) {

            addSheepVaterField.setText(vaterView.getSelectionModel().getSelectedItem().toString().split("\\|")[0]);
        }
    }

    //Schreibe ins Mutter-Textfield das ausgewählte Mutterschaf ohne Zusatzinfo
    public void mutterViewClicked(MouseEvent mouseEvent) {
        if (mutterView.getSelectionModel().getSelectedItem() != null) {

            addSheepMutterField.setText(mutterView.getSelectionModel().getSelectedItem().toString().split("\\|")[0]);
        }
    }

    //Markire den zukaufbutton
    public void zukaufClicked(ActionEvent actionEvent) {
        zukaufButton.setDisable(true);
        zukaufButton.setStyle("-fx-background-color: #EE6055");
        ablammungButton.setStyle("");
        ablammungButton.setDisable(false);

    }

    //Markire den ablammungsButton
    public void ablammungClicked(ActionEvent actionEvent) {
        ablammungButton.setDisable(true);
        ablammungButton.setStyle("-fx-background-color: #EE6055");
        zukaufButton.setStyle("");
        zukaufButton.setDisable(false);
    }
}


