package application.ModifyTables;

import application.Controller;
import application.Main;
import application.Model.Database;
import javafx.fxml.FXMLLoader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ModifySheep {

    static boolean editConfirmation = false;

    public static void editSheep(String bez, String type, String selection, String oldValue, String newValue) {

        //!!Wichtig!! Tabelle und Spalte der Scene müssen gleich heißen
        // editSheep kann nur verwendet werden wenn die Tabelle und die Spalte in der Scene den gleichen namen besitzen, da der Name der Spalte als Referenz für die Tabelle genommen wird
        Connection c = Database.getInstance();
        PreparedStatement statement = null;

        //Ausgeben der ALertbox
        Alerts.confirmBox("Sind Sie sicher den Wert von : " + bez + " In Spalte " + selection + " zu ändern\n" +
                "" + oldValue + " ---> " + newValue, "Änderung Speichern ?");

        // Wenn ok gedrückt wurde
        if (editConfirmation == true) {
            try {
                //prüfe ob Daten der Tier-Tabelle bearbeitet werden
                if (selection.matches("Geb_Daum|Schaftyp|Sonstige_Informationen|Weide|Farbe|Mutter_bez|Vater_bez")) {

                    statement = c.prepareStatement("Update sbrandst_Tiere SET " + selection + " = ? WHERE Schaf_bez = ?");

                    statement.setString(1, newValue);
                    statement.setString(2, bez);

                    statement.executeUpdate();

                    // Schreibe in Changelog (Action = CHANGE)
                    Main.mainController.addToChangelogChangeSheep(selection, bez, oldValue, newValue);

                } else {
                    // prüfe ob Schaf_bez in der Tiere Tabelle geändert wurde (Da der Wert dann auch in z.B Lämmer geändert werden muss)
                    if (selection.equals("Schaf_bez")) {
                        selection = type + "_bez"; // je nach schaftyp wird _bez angehängt um die Änderung auch in den Untertabellen zu ändern
                    }
                    //je nach Schftyp andere Tabelle Updaten
                    switch (type) {
                        case "Mutterschaf":
                            statement = c.prepareStatement("UPDATE sbrandst_Mutterschaf SET " + selection + " = ? WHERE  Mutterschaf_bez = ?");
                            statement.setString(1, newValue);
                            statement.setString(2, bez);
                            break;

                        case "Lamm":
                            statement = c.prepareStatement("UPDATE sbrandst_Laemmer SET " + selection + "=? WHERE Lamm_bez = ?");
                            statement.setString(1, newValue);
                            statement.setString(2, bez);
                            break;

                        case "Widder":
                            statement = c.prepareStatement("UPDATE sbrandst_Widder SET " + selection + "=? WHERE Widder_bez = ?");
                            statement.setString(1, newValue);
                            statement.setString(2, bez);
                            break;
                    }

                    statement.executeUpdate(); // Ändern der Einträge

                    statement.close();
                    c.close();

                }
            } catch (Exception e) {
                e.printStackTrace();
                Alerts.errorBox("Ein Fehler beim Ändern ist aufgetreten", "Error");
                return;
            }
            Main.mainController.addToChangelogChangeSheep(selection, bez, oldValue, newValue);

        }
    }

    public static void deleteSheep(String bez, String color, String type) {
        Connection c = Database.getInstance();
        PreparedStatement statement = null;

        try {
            // Ohrmackerl vom Gelöschten Schaf steht nun wieder zur verfügung
            statement = c.prepareStatement("INSERT INTO sbrandst_Ohrmackerl (Ohrmackerl_Bez) VALUES (?)");
            statement.setString(1, color);
            statement.executeUpdate();

            //Aus Tiere Tabelle löschen
            statement = c.prepareStatement("Delete FROM sbrandst_Tiere WHERE Schaf_bez = ?");

            statement.setString(1, bez);
            statement.executeUpdate();

            //aus den anderen Tabellen löschen
            if ("Mutterschaf".equals(type)) {
                statement = c.prepareStatement("DELETE FROM sbrandst_Mutterschaf " + " WHERE Mutterschaf_bez = ?");
                statement.setString(1, bez);


            } else if ("Lamm".equals(type)) {
                statement = c.prepareStatement("DELETE FROM sbrandst_Laemmer " + "WHERE Lamm_bez = ?");
                statement.setString(1, bez);


            } else if ("Widder".equals(type)) {
                statement.setString(1, bez);
            }

            statement.executeUpdate();

            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            Alerts.errorBox("Ein Fehler ist beim Löschen aufgetreten", "Error");
            return;
        }
        Main.mainController.addToChangelogDeleteSheep(bez, type, color);
    }
}
