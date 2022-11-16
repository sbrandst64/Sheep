package application.Mutterschafe;

import application.Model.Mutterschaf;
import application.ModifyTables.ModifySheep;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;

public class MutterschafController {
    public Label geb_Date_Label;
    public Label sheep_typ_Label;
    public Label ort_Label;
    public Label farbe_Label;
    public Label information_Label;
    public Label schafBez_Label;
    public Label geb_Anzahl_Label;
    public ListView<Mutterschaf> kinder_Listview; //ToDo
    public TableColumn<Mutterschaf, String> schafBezTable;
    public TableColumn<Mutterschaf, String> standortTable;
    public TableColumn<Mutterschaf, String> sheeptypTable;
    public TableColumn<Mutterschaf, String> gebDateTable;
    public TableColumn<Mutterschaf, String> farbeTable;
    public TableColumn<Mutterschaf, String> mutterTable;
    public TableColumn<Mutterschaf, String> vaterTable;
    public TableColumn<Mutterschaf, String> infoTable;
    public javafx.scene.control.TableView<Mutterschaf> mutterschafAnsicht;
    public TableColumn<Mutterschaf, String> gebAnzahlTable;

    public void initialize() {
        schafBezTable.setCellValueFactory(new PropertyValueFactory<>("bez"));
        schafBezTable.setCellFactory(TextFieldTableCell.forTableColumn());

        standortTable.setCellValueFactory(new PropertyValueFactory<>("weide"));
        standortTable.setCellFactory(TextFieldTableCell.forTableColumn());

        sheeptypTable.setCellValueFactory(new PropertyValueFactory<>("schaftyp"));
        sheeptypTable.setCellFactory(TextFieldTableCell.forTableColumn());

        gebDateTable.setCellValueFactory(new PropertyValueFactory<>("geb_Date"));
        gebDateTable.setCellFactory(TextFieldTableCell.forTableColumn());

        farbeTable.setCellValueFactory(new PropertyValueFactory<>("color"));
        farbeTable.setCellFactory(TextFieldTableCell.forTableColumn());

        mutterTable.setCellValueFactory(new PropertyValueFactory<>("mutter"));
        mutterTable.setCellFactory(TextFieldTableCell.forTableColumn());

        vaterTable.setCellValueFactory(new PropertyValueFactory<>("vater"));
        vaterTable.setCellFactory(TextFieldTableCell.forTableColumn());

        infoTable.setCellValueFactory(new PropertyValueFactory<>("infos"));
        infoTable.setCellFactory(TextFieldTableCell.forTableColumn());

        // --------------Mutterschaf-Specific--------------------------------
        refresh();
    }

    public void refresh() {
        mutterschafAnsicht.setItems(Mutterschaf.loadMutter());

    }

    public void edit_Clicked(ActionEvent actionEvent) {
        mutterschafAnsicht.setEditable(true);
    }

    public void delete_Clicked(ActionEvent actionEvent) {
        ModifySheep.deleteSheep(mutterschafAnsicht.getSelectionModel().getSelectedItem().getBez(),
                mutterschafAnsicht.getSelectionModel().getSelectedItem().getColor(),
                mutterschafAnsicht.getSelectionModel().getSelectedItem().getSchaftyp());
        refresh();
    }

    public void column_Commit(TableColumn.CellEditEvent<Mutterschaf, String> mutterschafStringCellEditEvent) {
        ModifySheep.editSheep(mutterschafAnsicht.getSelectionModel().getSelectedItem().getBez(),
                mutterschafAnsicht.getSelectionModel().getSelectedItem().getSchaftyp(),
                mutterschafStringCellEditEvent.getTablePosition().getTableColumn().getText(),
                mutterschafStringCellEditEvent.getOldValue(),
                mutterschafStringCellEditEvent.getNewValue());

        refresh();
    }


}