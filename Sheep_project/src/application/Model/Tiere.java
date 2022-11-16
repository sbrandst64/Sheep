package application.Model;

import application.ModifyTables.FilteredController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Tiere {
    protected String bez;
    protected String color;
    protected String geb_Date;
    protected Date death_Date = null;
    protected String weide;
    protected String schaftyp;
    protected String infos;
    protected String mutter;
    protected String vater;


    // erstelle neues Schafe Projekt
    public Tiere(String bez, String geb_Date, String schaftyp, String infos, String weide, String color, String mutter, String vater) {
        this.bez = bez;
        this.color = color;
        this.geb_Date = geb_Date;
        this.weide = weide;
        this.schaftyp = schaftyp;
        this.infos = infos;
        this.mutter = mutter;
        this.vater = vater;

    }

    //Hole alle Tier Objekte von der Datenbank
    public static ObservableList<Tiere> loadAll() {
        ObservableList<Tiere> result = FXCollections.observableArrayList();
        try {
            Connection c = Database.getInstance();
            PreparedStatement statement = c.prepareStatement("SELECT * FROM sbrandst_Tiere");
            getFromDatabase(result, statement);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static void getFromDatabase(ObservableList<Tiere> sheeps, PreparedStatement statement) throws SQLException {
        ResultSet results = statement.executeQuery();
        while (results.next()) {
            sheeps.add(new Tiere(results.getString("Schaf_bez"),
                    results.getString("Geb_Datum"),
                    results.getString("Schaftyp"),
                    results.getString("Sonstige_Informationen"),
                    results.getString("Weide"),
                    results.getString("Farbe"),
                    results.getString("Mutter_bez"),
                    results.getString("Vater_bez")));
        }

        results.close();
        statement.close();
    }

    public String getBez() {
        return bez;
    }

    public String getColor() {
        return color;
    }

    public String getGeb_Date() {
        return geb_Date;
    }

    public String getWeide() {
        return weide;
    }

    public String getSchaftyp() {
        return schaftyp;
    }

    public String getInfos() {
        return infos;
    }

    public String getVater() {
        return vater;
    }

    public String getMutter() {
        return mutter;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setWeide(String weide) {
        this.weide = weide;
    }

    public void setSchaftyp(String schaftyp) {
        this.schaftyp = schaftyp;
    }

    @Override
    public String toString() {
        return bez;
    }
}
