package application.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class Mutterschaf extends Tiere {

    public int numberOfBirths;

    // erstelle ein Mutterschaf
    public Mutterschaf(String bez, String geb_Date, String schaftyp, String infos, String weide, String color, int numberOfBirths, String mutter, String vater) {

        super(bez, geb_Date, schaftyp, infos, weide, color, mutter, vater);
        this.numberOfBirths = numberOfBirths;
    }
// lade Alle Mutterschaf Objekte
    public static ObservableList<Mutterschaf> loadMutter() {

        ObservableList<Mutterschaf> result = FXCollections.observableArrayList();
        try {

            Connection c = Database.getInstance();
            PreparedStatement statement = c.prepareStatement("SELECT * FROM sbrandst_Mutterschaf join sbrandst_Tiere sT on sbrandst_Mutterschaf.Mutterschaf_bez = sT.Schaf_bez");
            ResultSet results = statement.executeQuery();

            while (results.next()) {
                result.add(new Mutterschaf(results.getString("Schaf_bez"),
                        results.getString("Geb_Datum"),
                        results.getString("Schaftyp"),
                        results.getString("Sonstige_Informationen"),
                        results.getString("Weide"),
                        results.getString("Farbe"),
                        results.getInt("Anzahl_an_Geburten"),
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

}