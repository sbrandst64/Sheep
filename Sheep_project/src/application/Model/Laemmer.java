package application.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Laemmer extends Tiere {

    public String weight;
    public float weightPerMonth;
    public String father;
    public String mother;
    public String birthType;
    public String bez;
    public String mutter;
    public String vater;

    //Erstellle Lämmer Objekt
    public Laemmer(String bez, String geb_Date, String schaftyp, String infos, String weide, String color,
                   String weight, float weightPerMonth, String birthType, String mutter, String vater) {

        //hole Daten von Tiere
        super(bez, geb_Date, schaftyp, infos, weide, color, mutter, vater);

        this.bez=bez;
        this.weight = weight;
        this.weightPerMonth = weightPerMonth;
        this.birthType = birthType;
    }
    //Lade alle Lämmer von der Tabelle
    public static ObservableList<Laemmer> loadLaemmer() {

        ObservableList<Laemmer> res = FXCollections.observableArrayList();

        try {
            Connection c = Database.getInstance();
            PreparedStatement statement = c.prepareStatement("SELECT * FROM sbrandst_Laemmer join sbrandst_Tiere sT on sbrandst_Laemmer.Lamm_bez = sT.Schaf_bez");
            ResultSet results = statement.executeQuery();

            while (results.next()) {

                res.add(new Laemmer(results.getString("Schaf_bez"),
                        results.getString("Geb_Datum"),
                        results.getString("Schaftyp"),
                        results.getString("Sonstige_Informationen"),
                        results.getString("Weide"),
                        results.getString("Farbe"),
                        results.getString("Gewicht_in_KG"),
                        results.getFloat("Gewichtzunahme/Monat_in_KG"),
                        results.getString("Geburtsart"),
                        results.getString("Mutter_bez"),
                        results.getString("Vater_bez")));


            }
            results.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;

    }



    public String getBez() {
        return bez;
    }

    public String getWeight() {
        return weight;
    }

    public float getWeightPerMonth() {
        return weightPerMonth;
    }

    public String getFather() {
        return father;
    }

    public String getMother() {
        return mother;
    }

    public String getBirthType() {
        return birthType;
    }



}