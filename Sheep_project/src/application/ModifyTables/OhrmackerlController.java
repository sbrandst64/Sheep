package application.ModifyTables;

import application.Controller;
import application.Main;
import application.Model.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
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

public class OhrmackerlController {
    public ListView<String> mackerlListView;
    public TextField addMackerlTextfield;
    public Button addMackerlButton;
    public Button entfMackerlButton;

    public void initialize() {

        mackerlListView.setItems(loadOhrmackerl());
    }

    //Lade alle Ohrmackerl in die übergebene Listview
    public static ObservableList loadOhrmackerl() {

        ObservableList<String> result = FXCollections.observableArrayList();
        try {
            Connection c = Database.getInstance();
            PreparedStatement statement = c.prepareStatement("SELECT * FROM sbrandst_Ohrmackerl ");
            ResultSet results = statement.executeQuery();

            while (results.next()) {
                result.add((results.getString("Ohrmackerl_Bez")));
            }
            results.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // Entfernen eines Ohrmackerls aus der Listview
    public void entfSelectedMackerl(MouseEvent mouseEvent) {
        String selected = mackerlListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Connection c = Database.getInstance();
            PreparedStatement statement = null;
            try {
                statement = c.prepareStatement("DELETE FROM sbrandst_Ohrmackerl WHERE Ohrmackerl_Bez = ? ");
                statement.setString(1, String.valueOf(selected));

                statement.executeUpdate();
                statement.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
            Main.mainController.addToChangelogMackerlDelete(mackerlListView.getSelectionModel().getSelectedItem());
            initialize();
        }
    }

    // Hinzufügen eines Ohrmackerls zu der Listview
    public void addMackerlClicked(MouseEvent mouseEvent) {

        Connection c = Database.getInstance();
        PreparedStatement statement = null;
        String mackerl = addMackerlTextfield.getText();

        try {
            statement = c.prepareStatement("INSERT INTO sbrandst_Ohrmackerl (Ohrmackerl_Bez) VALUES (?) ");
            statement.setString(1, mackerl);

            statement.executeUpdate();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            Alerts.errorBox("Ein Fehler ist aufgetreten", "Error");
            return;
        }
        Main.mainController.addToChangelogAddMackerl(addMackerlTextfield.getText());

        addMackerlTextfield.clear();  //Eingabe Textfield leeren
        initialize();

    }
}

