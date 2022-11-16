package application.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Widder extends Tiere {


    public String deckDatumVon;
    public String name;
    public String deckDatumBis;
    public String bez;
    public String mutter;
    public String vater;

    //Erstelle Widder Objekt
    public Widder(String bez, String geb_Date, String schaftyp, String infos, String weide, String color,
                  String name, String deckDatumVon, String deckDatumBis, String mutter, String vater) {

        super(bez, geb_Date, schaftyp, infos, weide, color, mutter, vater);

        this.bez = bez;
        this.name = name;
        this.deckDatumVon = deckDatumVon;
        this.deckDatumBis = deckDatumBis;

    }

//Lade alle Widder Objekte
    public static ObservableList<Widder> loadWidder() {

        ObservableList<Widder> result = FXCollections.observableArrayList();

        try {
            Connection c = Database.getInstance();
            PreparedStatement statement = c.prepareStatement("SELECT * FROM sbrandst_Widder sW join sbrandst_Tiere sT on sW.Widder_bez = sT.Schaf_bez ");
            ResultSet results = statement.executeQuery();

            while (results.next()) {
                result.add( new Widder(results.getString("Widder_bez"),
                        results.getString("Geb_Datum"),
                        results.getString("Schaftyp"),
                        results.getString("Sonstige_Informationen"),
                        results.getString("Weide"),
                        results.getString("Farbe"),
                        results.getString("Name"),
                        results.getString("Deckdatum_von"),
                        results.getString("Deckdatum_bis"),
                        results.getString("Mutter_bez"),
                        results.getString("Vater_bez")));
            }
            results.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }


    public String getBez() {
        return bez;
    }

    public String getDeckDatumVon() {
        return deckDatumVon;
    }

    public String getName() {
        return name;
    }

    public String getDeckDatumBis() {
        return deckDatumBis;
    }
}

