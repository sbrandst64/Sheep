package application;

import application.Model.Tiere;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static Controller mainController;

    /**
     * main method
     *
     * @param primaryStage
     * @throws Exception
     */

    @Override
    //ToDo Genauere Betrachtung
    //ToDo Kinder Listview
    //ToDo GVE-View (Graf Switch Id)
    //ToDo last-Update
    //ToDo LazyLoading
    //ToDo weiden Fläche hinzufügen
    //ToDo alle SQL statements in eigene Klasse auslagern
    //Nice to know
    //Mackerl == Farbe

    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("scene.fxml"));
        primaryStage.setTitle("Mollnhub-Schafe");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        Tiere.loadAll();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
