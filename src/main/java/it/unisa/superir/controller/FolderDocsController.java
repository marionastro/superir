package it.unisa.superir.controller;

import it.unisa.superir.SuperIR;
import it.unisa.superir.model.Document;
import it.unisa.superir.model.QueryExecution;
import it.unisa.superir.model.Score;
import it.unisa.superir.service.QueryExecutionService;
import it.unisa.superir.view.DocView;
import it.unisa.superir.view.QueryInsertView;
import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Controller used for interactions and updates on the FolderDocsView.
 * @author Gruppo5
 */
public class FolderDocsController implements Initializable, Statistical, Loader {
    @FXML private CheckBox showIrrelevantCheckBox;
    @FXML private VBox statsRootPane;
    @FXML private FlowPane documentsContainer;
    @FXML private TextField queryField;
    @FXML private Text folderName;
    @FXML private VBox loadingPane;
    @FXML private BorderPane contentPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        QueryExecutionService service = SuperIR.getInstance().getQueryExecutionService();

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

    /**
     * Function to run to set up the FolderDocsView.
     * @param queryExecution 
     */
    private void setup(QueryExecution queryExecution) {
        folderName.setText(queryExecution.getFolder().getName());
        queryField.setText(queryExecution.getQuery());

        setLoading(false);

        showIrrelevantCheckBox.setOnAction(e -> showDocuments(queryExecution, showIrrelevantCheckBox.isSelected()));
        showIrrelevantCheckBox.fire();

        addStats("Numero di documenti", String.valueOf(queryExecution.getFolder().getDocuments().size()), false);
        addStats("Numero di documenti rilevanti", String.valueOf(
                queryExecution.getFolder().getDocuments()
                        .stream()
                        .filter(d -> queryExecution.getScore(d).getTotalScore() > 0)
                        .count()),
                false);
        addStats("Lunghezza totale", String.valueOf(queryExecution.getFolder().getTotalWords()), false);
        addStats("Lunghezza media", String.valueOf(queryExecution.getFolder().getAverageDocumentsWords()), false);
        addStats("Vocabolario", queryExecution.getFolder().getVocabulary().toString(), true);
        addStats("Frequenza delle parole", queryExecution.getFolder().getOccurrences().entrySet()
                .stream()
                .filter(e -> e.getKey().matches("\\w+"))
                .sorted((o1, o2) -> Long.compare(o2.getValue(), o1.getValue()))
                .map(e -> e.getKey() + ": " + e.getValue())
                .collect(Collectors.joining(", ")), true);
    }

    /**
     * Function to run to show a document on the List of documents
     * (the documents with a score of 0 are showed only if the "Show Irrelevant Docs" is selected).
     * 
     * @param queryExecution    the results of the execution of the IR algorithm.
     * @param irrelevant        boolean propriety that tells if the users wants to see the documents with null score
     */
    private void showDocuments(QueryExecution queryExecution, boolean irrelevant) {
        documentsContainer.getChildren().clear();

        for (Document document : queryExecution.getSorted()) {
            Score score = queryExecution.getScore(document);

            if (score.getTotalScore() > 0 || irrelevant) {
                addDocumentPane(document, queryExecution.getScore(document));
            }
        }
    }

    /**
     * Function to run to add a document to the Pane showing all the folder documents.
     * 
     * @param document  document to add to the pane
     * @param score     score of the document to add
     */
    private void addDocumentPane(Document document, Score score) {
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setCursor(Cursor.HAND);
        vBox.setPadding(new Insets(10, 10, 10, 10));
        vBox.setSpacing(10);

        Text documentName = new Text(document.getFile().getName());
        Font font = new Font(18);
        documentName.setFont(font);

        long scoreValue = score.getTotalScore();
        Text scoreText = new Text(scoreValue + "%");
        scoreText.setFont(font);

        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20, 20, 20, 20));
        borderPane.getStyleClass().add("documentPane");

        BorderPane scoreBackground = new BorderPane();
        scoreBackground.setCenter(scoreText);
        scoreBackground.setPadding(new Insets(30, 30, 30, 30));
        scoreBackground.getStyleClass().add("scoreBackground");

        if (scoreValue >= 70) {
            scoreBackground.getStyleClass().add("scoreBackgroundGreen");
        } else if (scoreValue >= 50) {
            scoreBackground.getStyleClass().add("scoreBackgroundOrange");
        } else if (scoreValue >= 30) {
            scoreBackground.getStyleClass().add("scoreBackgroundYellow");
        } else {
            scoreBackground.getStyleClass().add("scoreBackgroundRed");
        }

        borderPane.setCenter(scoreBackground);

        vBox.getChildren().add(borderPane);
        vBox.getChildren().add(documentName);

        vBox.setOnMouseClicked(event -> {
            if (event.getClickCount() >= 2) {
                SuperIR.getInstance().setSelectedDocument(document);
                new DocView().show();
            }
        });

        documentsContainer.getChildren().add(vBox);
    }

    /**
     * Function to go back to the previous view (QueryInsertView).
     * @param event 
     */
    @FXML
    private void back(ActionEvent event) {
        new QueryInsertView().show();
    }

    /**
     * Function to run to obtain the Pane containing the folder statistics.
     * @return the Pane containing the folder statistics.
     */
    @Override
    public VBox getStatsContainer() {
        return statsRootPane;
    }

    /**
     * Function to run to obtain the loading pane.
     * @return the loading pane.
     */
    @Override
    public Pane getLoadingPane() {
        return loadingPane;
    }

    /**
     * Function to run to obtain the list of all the documents scores.
     * @return the list of all the documents scores.
     */
    @Override
    public Pane getContentPane() {
        return contentPane;
    }
}
