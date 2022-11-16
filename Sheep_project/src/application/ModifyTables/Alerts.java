package application.ModifyTables;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class Alerts {

    public static void infoBox(String infoMessage, String titleBar) {

        infoBox(infoMessage, titleBar, null);
    }
    //Funktion wird überladen. Wenn also kein header gesetzt wird steht keiner drin wenn doch steht der drin
    public static void infoBox(String infoMessage, String titleBar, String headerMessage)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titleBar);
        alert.setHeaderText(headerMessage);
        alert.setContentText(infoMessage);
        alert.showAndWait();
    }

    public static void confirmBox(String confirmMessage, String titleBar) {

        confirmBox(confirmMessage, titleBar, null);
    }

//Funktion wird überladen. Wenn also kein header gesetzt wird steht keiner drin wenn doch steht dieser drin
    public static  void confirmBox(String infoMessage, String titleBar, String headerMessage) {
        //Erstelle Confirmation Alert
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(titleBar);
        alert.setHeaderText(headerMessage);
        alert.setContentText(infoMessage);
        Optional<ButtonType> result = alert.showAndWait();

        if (!result.isPresent()) {
            // alert is exited, no button has been pressed.

            //Prüfen ob Ok oder cancel gedrückt wurde
        } else if (result.get() == ButtonType.OK) {
           ModifySheep.editConfirmation = true;
        } else if (result.get() == ButtonType.CANCEL) {
            ModifySheep.editConfirmation = false;

        }
    }

    public static void errorBox(String errorMessage, String titleBar) {

        errorBox(errorMessage, titleBar, null);
    }

//Funktion wird überladen. Wenn also kein header gesetzt wird steht keiner drin wenn doch steht der drin
    public static void errorBox (String errorMessage, String titleBar, String headerMessage)  {
        //erstelle ERROR ALERT
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titleBar);
        alert.setHeaderText(headerMessage);
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }

}

