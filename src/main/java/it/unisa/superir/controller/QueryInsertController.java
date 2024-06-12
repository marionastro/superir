package it.unisa.superir.controller;

import it.unisa.superir.SuperIR;
import it.unisa.superir.algorithm.StandardIR;
import it.unisa.superir.algorithm.TFIDF;
import it.unisa.superir.model.Folder;
import it.unisa.superir.service.FolderLoaderService;
import it.unisa.superir.service.QueryExecutionService;
import it.unisa.superir.view.FolderDocsView;
import it.unisa.superir.view.FolderSelectionView;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QueryInsertController implements Initializable, Loader {
    @FXML private Slider relevanceSlider;
    @FXML private Text titleRelevance;
    @FXML private Text bodyRelevance;
    @FXML private BorderPane contentPane;
    @FXML private VBox loadingPane;
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

        setLoading(true);

        if (service != null) {
            if (service.getState() != Worker.State.SUCCEEDED) {
                service.restart();
                service.setOnSucceeded(event -> setup(service.getValue()));
                service.setOnFailed(event -> {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, service.getException());
                    Alert alert = new Alert(Alert.AlertType.ERROR, service.getException().getMessage(), ButtonType.CLOSE);
                    alert.showAndWait().ifPresent(c -> Platform.exit());
                });
            } else {
                setup(service.getValue());
            }
        }
    }

    private void setup(Folder folder) {
        folderName.setText(folder.getName());

        setLoading(false);

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
                    relevanceSlider.getValue()
            );

            SuperIR.getInstance().setQueryExecutionService(service);
            new FolderDocsView().show();
        });

        ContextMenu contextMenu = new ContextMenu();
        MenuItem deleteMenuItem = new MenuItem("Cancella");
        deleteMenuItem.setOnAction(e -> {
            File selected = stopWordsFilesTable.getSelectionModel().getSelectedItem();
            stopWordsFilesTable.getItems().remove(selected);
        });
        stopWordsFilesTable.setEditable(true);
        contextMenu.getItems().add(deleteMenuItem);
        stopWordsFilesTable.setContextMenu(contextMenu);
    }

    @FXML private void toggleAdvancedOptions(ActionEvent event) {
        advancedOptionsPane.setVisible(!advancedOptionsPane.isVisible());
    }

    @FXML private void back(ActionEvent event) {
        new FolderSelectionView().show();
    }

    @FXML private void addStopWordsFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleziona il file con le Stop Words.");
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("TXT Files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(SuperIR.getInstance().getCurrentStage());

        if (file != null && file.isFile()) {
            if (!stopWordsFilesTable.getItems().contains(file))
                stopWordsFilesTable.getItems().add(file);
        }
    }

    @Override
    public Pane getLoadingPane() {
        return loadingPane;
    }

    @Override
    public Pane getContentPane() {
        return contentPane;
    }
}
