package it.unisa.superir.controller;

import it.unisa.superir.SuperIR;
import it.unisa.superir.model.Document;
import it.unisa.superir.service.QueryExecutionService;
import it.unisa.superir.view.FolderDocsView;
import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.effect.BlendMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Controller used for interactions and updates on the DocView.
 * @author Gruppo5
 */
public class DocController implements Initializable, Statistical, Loader {
    @FXML private VBox statsRootPane;
    @FXML private TextField queryField;
    @FXML private Text fileName;
    @FXML private BorderPane contentPane;
    @FXML private VBox loadingPane;
    @FXML private WebView webView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        QueryExecutionService service = SuperIR.getInstance().getQueryExecutionService();
        Document selectedDocument = SuperIR.getInstance().getSelectedDocument();

        setLoading(true);

        if (service != null) {
            if (service.getState() != Worker.State.SUCCEEDED) {
                service.restart();
                service.setOnSucceeded(event -> setup(service, selectedDocument));
                service.setOnFailed(event -> {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, service.getException());
                    Alert alert = new Alert(Alert.AlertType.ERROR, service.getException().getMessage(), ButtonType.CLOSE);
                    alert.showAndWait().ifPresent(c -> Platform.exit());
                });
            } else {
                setup(service, selectedDocument);
            }
        }
    }

    private void setup(QueryExecutionService service, Document selectedDocument) {
        fileName.setText(selectedDocument.getFile().getName());
        queryField.setText(service.getValue().getQuery());
        String webViewContent = String.format(
                "<h1><b>%s</b></h1><p>%s</p>",
                selectedDocument.getTitle().getJoining(),
                selectedDocument.getBody().getJoining()
        );
        webView.getEngine().loadContent(webViewContent, "text/html");
        webView.getEngine().setUserStyleSheetLocation(getClass().getResource("/styles/web.css").toExternalForm());
        webView.setBlendMode(BlendMode.DARKEN); // Trick to make background transparent

        setLoading(false);
        addStats(
                "Score",
                String.format(
                        "Titolo: %d%%\nCorpo: %d%%\nTotale: %d%%",
                        service.getValue().getScore(selectedDocument).getTitleScore(),
                        service.getValue().getScore(selectedDocument).getBodyScore(),
                        service.getValue().getScore(selectedDocument).getTotalScore()
                ),
                true
        );
        addStats("Lunghezza", String.valueOf(selectedDocument.getLength()), false);
        addStats("Vocabolario", selectedDocument.getVocabulary().toString(), true);
        addStats("Frequenza delle parole", selectedDocument.getOccurrences().entrySet()
                .stream()
                .filter(e -> e.getKey().matches("\\w+"))
                .sorted((o1, o2) -> Long.compare(o2.getValue(), o1.getValue()))
                .map(e -> e.getKey() + ": " + e.getValue())
                .collect(Collectors.joining(", ")), true);
    }

    /**
     * Function to go back to the previous view (FolderDocsView).
     * @param event 
     */
    @FXML private void back(ActionEvent event) {
        new FolderDocsView().show();
    }

    /**
     * Function to run to obtain the pane containing the documents Statistics.
     * @return the pane containing the documents Statistics.
     */
    @Override
    public VBox getStatsContainer() {
        return statsRootPane;
    }

    /**
     * Function to run to obtain the loading pane.
     * @return  the loading pane.
     */
    @Override
    public Pane getLoadingPane() {
        return loadingPane;
    }

    /**
     * Function to run to obtain the Pane containing the document content.
     * @return the Pane containing the document content.
     */
    @Override
    public Pane getContentPane() {
        return contentPane;
    }
}
