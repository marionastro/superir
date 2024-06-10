package it.unisa.superir.controller;

import it.unisa.superir.SuperIR;
import it.unisa.superir.algorithm.StandardIR;
import it.unisa.superir.algorithm.TFIDF;
import it.unisa.superir.model.Folder;
import it.unisa.superir.service.FolderLoaderService;
import it.unisa.superir.service.QueryExecutionService;
import it.unisa.superir.view.FolderDocsView;
import it.unisa.superir.view.FolderSelectionView;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class QueryInsertController implements Initializable {
    @FXML public Slider relevanceSlider;
    @FXML public Text titleRelevance;
    @FXML public Text bodyRelevance;
    @FXML private TextField queryField;
    @FXML private Button executeButton;
    @FXML private TableColumn<File, String> stopWordsFileColumn;
    @FXML private TableView<File> stopWordsFilesTable;
    @FXML private ComboBox<String> irComboBox;
    @FXML private Text folderName;
    @FXML private AnchorPane advancedOptionsPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FolderLoaderService service = SuperIR.getInstance().getFolderLoaderService();

        if (service != null) {
            if (!service.isRunning())
                service.restart();

            service.setOnSucceeded(event -> setup(service.getValue()));
            service.setOnFailed(event -> {
                // TODO
            });
        }
    }

    private void setup(Folder folder) {
        folderName.setText(folder.getName());
        irComboBox.getItems().add("Standard");
        irComboBox.getItems().add("TF-IDF");
        irComboBox.getSelectionModel().select(0);

        StringBinding titleRelevanceBinding = Bindings.createStringBinding(
                () -> Math.round(relevanceSlider.valueProperty().get()) + "%",
                relevanceSlider.valueProperty()
        );

        StringBinding bodyRelevanceBinding = Bindings.createStringBinding(
                () -> Math.round(100 - relevanceSlider.valueProperty().get()) + "%",
                relevanceSlider.valueProperty()
        );

        titleRelevance.textProperty().bind(titleRelevanceBinding);
        bodyRelevance.textProperty().bind(bodyRelevanceBinding);

        stopWordsFileColumn.setCellValueFactory(new PropertyValueFactory<>("path"));
        executeButton.disableProperty().bind(queryField.textProperty().isEmpty());

        executeButton.setOnAction(event -> {
            QueryExecutionService service = new QueryExecutionService(
                    folder,
                    queryField.getText(),
                    stopWordsFilesTable.getItems(),
                    irComboBox.getSelectionModel().getSelectedIndex() == 0 ? new StandardIR() : new TFIDF(folder),
                    relevanceSlider.getValue() / 10
            );

            SuperIR.getInstance().setQueryExecutionService(service);
            new FolderDocsView().show();
        });
    }

    @FXML private void toggleAdvancedOptions(ActionEvent event) {
        advancedOptionsPane.setVisible(!advancedOptionsPane.isVisible());
    }

    @FXML private void back(ActionEvent event) {
        new FolderSelectionView().show();
    }

    @FXML private void addStopWordsFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleziona il file contenenti le Stop Words.");
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("TXT Files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(SuperIR.getInstance().getCurrentStage());

        if (file != null && file.isFile()) {
            if (!stopWordsFilesTable.getItems().contains(file))
                stopWordsFilesTable.getItems().add(file);
        }
    }
}
