package application.Widder;

import application.Model.Widder;
import application.ModifyTables.ModifySheep;
import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;


public class WidderController {

    public TableView<Widder> widderTable;
    public TableColumn<Widder, String> schafBezTable;
    public TableColumn<Widder, String> standortTable;
    public TableColumn<Widder, String> sheeptypTable;
    public TableColumn<Widder, String> gebDateTable;
    public TableColumn<Widder, String> farbeTable;
    public TableColumn<Widder, String> mutterTable;
    public TableColumn<Widder, String> vaterTable;
    public TableColumn<Widder, String> infoTable;
    public TableColumn<Widder, String> nameTable;
    public TableColumn<Widder, String> deckdatumVonTable;
    public TableColumn<Widder, String> deckdatumBisTable;
    public MenuItem edit;
    public MenuItem delete;


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
//---------------------------------------------------------------------------------------------Widder specific
        nameTable.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameTable.setCellFactory(TextFieldTableCell.forTableColumn());

        deckdatumVonTable.setCellValueFactory(new PropertyValueFactory<>("deckDatumVon"));
        deckdatumVonTable.setCellFactory(TextFieldTableCell.forTableColumn());

        deckdatumBisTable.setCellValueFactory(new PropertyValueFactory<>("deckDatumBis"));
        deckdatumBisTable.setCellFactory(TextFieldTableCell.forTableColumn());

        refresh();

    }


    public void refresh() {
        widderTable.setItems(Widder.loadWidder());
    }


    public void edit_Clicked(ActionEvent actionEvent) {
        widderTable.setEditable(true);

    }

    public void delete_Clicked(ActionEvent actionEvent) {

        ModifySheep.deleteSheep(widderTable.getSelectionModel().getSelectedItem().getBez(),
                widderTable.getSelectionModel().getSelectedItem().getColor(),
                widderTable.getSelectionModel().getSelectedItem().getSchaftyp());
        refresh();
    }

    public void edit_Commit(TableColumn.CellEditEvent<Widder, String> widderStringCellEditEvent) {


        ModifySheep.editSheep(widderTable.getSelectionModel().getSelectedItem().getBez(),
                widderTable.getSelectionModel().getSelectedItem().getSchaftyp(),
                widderStringCellEditEvent.getTablePosition().getTableColumn().getText(),
                widderStringCellEditEvent.getOldValue(),
                widderStringCellEditEvent.getNewValue());

        refresh();
    }


}
