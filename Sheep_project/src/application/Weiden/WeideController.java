package application.Weiden;

import application.Controller;
import application.Main;
import application.Model.Database;
import application.ModifyTables.Alerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class WeideController {
    public ListView weidenListView;
    public TextField addWeideNameTextfield;
    public TextField addWeideFlächeTextfield;
    public Label numberWidder;
    public Label numberMutter;
    public Label numberLämmer;
    public ListView widderView;
    public ListView muetterView;
    public ListView laemmerView;
    public Label mutterCount;
    public Label laemmerCount;
    public Label widderCount;

    public void initialize() {
        weidenListView.setItems(loadWeiden());
    }

    //Befülle ListView mit allen aus der Weiden aus der Tabelle
    public static ObservableList loadWeiden() {
        ObservableList<String> result = FXCollections.observableArrayList();
        try {

            Connection c = Database.getInstance();
            PreparedStatement statement = c.prepareStatement("SELECT * FROM sbrandst_Weiden ");
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                result.add((results.getString("Weide")));
            }
            results.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // hinzufügen einer neuen Weide
    public void addWeideClicked(MouseEvent mouseEvent) {
        Connection c = Database.getInstance();
        PreparedStatement statement = null;
        String mackerl = addWeideNameTextfield.getText();
        String fläche = addWeideFlächeTextfield.getText();


        try {
            statement = c.prepareStatement("INSERT INTO sbrandst_Weiden (Weide,Fläche_in_ha) VALUES (?,?) ");
            statement.setString(1, mackerl);
            statement.setString(2, fläche);
            statement.executeUpdate();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        Main.mainController.addToChangelogAddWeide(addWeideNameTextfield.getText(), addWeideFlächeTextfield.getText());

        addWeideNameTextfield.clear();
        addWeideFlächeTextfield.clear();

        initialize();
    }

    //Löschen einer Weide
    public void entfSelectedWeide(MouseEvent mouseEvent) {
        String selected = weidenListView.getSelectionModel().getSelectedItem().toString();
        if (selected != null) {

            Connection c = Database.getInstance();
            PreparedStatement statement = null;
            ResultSet results = null;
            ObservableList<String> weideList = FXCollections.observableArrayList();
            try {
                statement = c.prepareStatement("SELECT * FROM sbrandst_Weiden sW join sbrandst_Tiere sT on sW.Weide = sT.Weide WHERE sW.Weide = ?");
                statement.setString(1, selected);
                results = statement.executeQuery();

                while (results.next()) {
                    weideList.add(results.getString("Weide"));
                }
                if (weideList.isEmpty()) {
                    statement = c.prepareStatement("DELETE FROM sbrandst_Weiden WHERE Weide = ? ");
                    statement.setString(1, weidenListView.getSelectionModel().getSelectedItem().toString());

                    statement.executeUpdate();
                    statement.close();
                    c.close();
                    Main.mainController.addToChangelogDeleteWeide(weidenListView.getSelectionModel().getSelectedItem().toString());

                } else {
                    //Gib Fehler aus, wenn Weide gelöscht werden sollte, aber noch Schafe darauf sind
                    Alerts.errorBox("Es sind noch Schafe auf der Weide", "Fehler beim Löschen der Weide");

                }
            } catch (SQLException e) {
                e.printStackTrace();
                //Unbekannter Fehler
                Alerts.errorBox(" Ein Fehler beim Löschen ist aufgetreten", "Error");

                return; // Beende den aktuellen Vorgang
            }
            initialize();
        }
    }

    //Anklicken einer Weide
    public void selectWeide(MouseEvent mouseEvent) {

        String selected = weidenListView.getSelectionModel().getSelectedItem().toString();

        if (selected != null) {
            Connection c = Database.getInstance();
            PreparedStatement statement = null;
            ResultSet results = null;
            muetterView.getItems().clear();
            widderView.getItems().clear();
            laemmerView.getItems().clear();

            try {
                //hole alle schafe auf der angeclickten Weide
                statement = c.prepareStatement("SELECT * FROM sbrandst_Tiere WHERE Weide = ?");
                statement.setString(1, selected);
                results = statement.executeQuery();

                while (results.next()) {
                    //Schreibe alle Schafe, die sich auf der Weide befinden, je nach typ in listviews
                    // für bessere Übersicht Farbe zusätzlich angehängt
                    switch (results.getString("Schaftyp")) {

                        case "Lamm":
                            laemmerView.getItems().add(results.getString("Schaf_bez") + " | " + results.getString("Farbe"));
                            break;

                        case "Widder":
                            widderView.getItems().add(results.getString("Schaf_bez") + " | " + results.getString("Farbe"));
                            break;

                        case "Mutterschaf":
                            muetterView.getItems().add(results.getString("Schaf_bez") + " | " + results.getString("Farbe"));
                            break;
                    }

                }
                //Zähle die anzahl an Schafen
                laemmerCount.setText(String.valueOf(laemmerView.getItems().size()));
                widderCount.setText(String.valueOf(widderView.getItems().size()));
                mutterCount.setText(String.valueOf(muetterView.getItems().size()));

                results.close();
                c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }
}