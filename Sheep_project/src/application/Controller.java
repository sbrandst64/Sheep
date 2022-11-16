package application;

import application.Model.Tiere;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Controller {

    public Button addSheep;
    public Button ohrMackerlButton;
    public BorderPane mainPane;
    public Button laemmerButton;
    public Button widderButton;
    public Button filterButton;
    public Button weidenButton;
    public Button mutterschafButton;
    public Button seeAllButton;
    public AnchorPane allPane;
    public static DateTimeFormatter time = DateTimeFormatter.ofPattern("HH:mm:ss - dd.MM.yyyy"); // Zeitpattern definieren
    private static Button lastButton; // letzter gedrückter Button
    public TextArea changelogText;

    public void initialize() throws IOException {
        loadUI("SeeALL/DatadisplaySeeALL");
        changelogText.setText(loadChangelogFromFile());
        Main.mainController = this;
        lastButton= seeAllButton; // der Erste gesetzte Button ist seeAll
    }
//##################################-Changelog-##################################
    //Liest die aktuellen Einträge im Changelog aus
    public String loadChangelogFromFile() {
        String readData = "";

        File myChangelog = new File("changelog.txt");
        Scanner readFromFile = null;
        try {
            readFromFile = new Scanner(myChangelog);

            while (readFromFile.hasNextLine()) {
                readData = readData + readFromFile.nextLine() + "\n"; //jede Zeile einlesen und in readData speichern

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return readData;

    }

    public void writeToChangelog(String data) {
        try {
            data = loadChangelogFromFile().concat(data); // anhängen der aktuellen Änderung

            FileWriter writeToFile = new FileWriter("changelog.txt");
            writeToFile.write(data);    // ins File schreiben
            writeToFile.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //-----------------HINZUFÜGEN-----------------
    //hinzufügen Schafe
    public void addToChangelogAddSheep(String bez, String mackerl, String type) {
        String data = "++Hinfügen von " + type + " mit Bezeichnung: " + bez + " und Farbe: " + mackerl;
        DisplayChangelog(data);

    }

    public void addToChangelogAddWeide(String weide, String fläche) {
        String data = "++Hinfügen von der Weide" + weide + " mit der Fläche :" + fläche;
        DisplayChangelog(data);

    }

    //hinzufügen Mackerl
    public void addToChangelogAddMackerl(String mackerl) {
        String data = "++Hinzufügen von Mackerl :" + mackerl;
        DisplayChangelog(data);

    }

    //-----------------DELETE-----------------
    //Delete Schaf
    public void addToChangelogDeleteSheep(String bez, String type, String mackerl) {
        String data = "--Löschte " + type + " mit Bezeichnung: " + bez + " und Mackerl: " + mackerl;
        DisplayChangelog(data);

    }

    //Delete Weide
    public void addToChangelogDeleteWeide(String weide) {
        String data = "--Löschte Weide: " + weide;
        DisplayChangelog(data);

    }

    //Delete Mackerl
    public void addToChangelogMackerlDelete(String mackerl) {
        String data = "--Löschte Mackerl: " + mackerl;
        DisplayChangelog(data);

    }

    //-----------------CHANGE-----------------
    //Change Sheep
    public void addToChangelogChangeSheep(String selection, String bez, String old, String now) {
        String data = "//Änderte von Schaf: \n" + bez + " die Spalte: \n" + selection + " von\n " + old + " zu : " + now;
        DisplayChangelog(data);

    }
//schreibt ins Changelog Textfield und txt // für Formatierungs-Zwecke
    public void DisplayChangelog(String data){
        writeToChangelog(data);
        changelogText.setText(changelogText.getText().concat("\n#["+Controller.time.format(LocalDateTime.now())+"]#\n"+data+"\n--------------------\n"));

    }
    //##################################-Wechseln-der-Scenes-##################################
    //Wechseln zum SEE ALL Bereich
    public void changedToSeeALL(Event event) throws IOException {
        loadUI("SeeALL/DatadisplaySeeALL");
        resetButtons(seeAllButton);
    }

    //Wechseln zum Mutterschafe Bereich
    public void changedToMutterschafe(Event event) throws IOException {
        loadUI("Mutterschafe/DataDisplayMutterschaf");
        resetButtons(mutterschafButton);

    }

    //Wechseln zum Lämmer Bereich
    public void changedToLaemmer(MouseEvent mouseEvent) throws IOException {
        loadUI("Laemmer/DataDisplayLaemmer");
        resetButtons(laemmerButton);
    }

    //Wechseln zum Widder Bereich
    public void changedToWidder(Event event) throws IOException {
        loadUI("Widder/DataDisplayWidder");
        resetButtons(widderButton);
    }

    //Wechseln zum Ohrmackerl Bereich
    public void changeToOhrmackerl(MouseEvent mouseEvent) throws IOException {
        loadUI("ModifyTables/Ohrmackerl");

        resetButtons(ohrMackerlButton);

    }

    //Wechseln zum Filter Bereich
    public void changedToFiltered(Event event) throws IOException {
        loadUI("ModifyTables/FilterView");

        resetButtons(filterButton);
    }

    public void changedToGVEInside(MouseEvent mouseEvent) throws IOException {
        loadUI("ModifyTables/GVE-Tabelle");
    }

    //Wechseln zum Weiden Bereich
    public void changedToWeiden(MouseEvent mouseEvent) throws IOException {
        loadUI("Weiden/DataDisplayWeiden");

        resetButtons(weidenButton);
    }

    public void resetButtons(Button nowButton) {
        // Farbe zurücksetzen des zuletzt gedrückten Buttons
       lastButton.setStyle("-fx-background-color: transparent");

        nowButton.setStyle("-fx-background-color: linear-gradient(#FF3366,#BA265D)");
        lastButton = nowButton;
    }

    // Lade die Scene in der ausgewählten Datei in die Mitte des Programms
    public Object loadUI(String scene) throws IOException {
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource(scene + ".fxml"));
        root = loader.load();
        Object controller = loader.getController();
        mainPane.setCenter(root);

        return controller;
    }

    //Work in Progress
    public void gve_Button_Clicked(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ModifyTables/GVE-Tabelle.fxml"));
        Parent root2 = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("GVE-Tabelle");
        stage.setScene(new Scene(root2));
        stage.show();
    }

    // Öffne ein externes Zukauf Fenster
    public void zukauf_Clicked(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ModifyTables/AddNewSheep.fxml"));
        Parent root1 = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Schafe Hinzufügen");
        stage.setScene(new Scene(root1));

        try {
            stage.showAndWait(); // warte bis Fenster geschlossen ist

        } finally {
            Tiere.loadAll();    //Wenn Fenster geschlossen, aktualisiere Tabelle
        }

    }


}

