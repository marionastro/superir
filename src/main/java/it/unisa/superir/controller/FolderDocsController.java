package it.unisa.superir.controller;

import it.unisa.superir.SuperIR;
import it.unisa.superir.model.Document;
import it.unisa.superir.model.QueryExecution;
import it.unisa.superir.model.Score;
import it.unisa.superir.service.QueryExecutionService;
import it.unisa.superir.view.DocView;
import it.unisa.superir.view.QueryInsertView;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class FolderDocsController implements Initializable {
    @FXML private FlowPane documentsContainer;
    @FXML private TextField queryField;
    @FXML private Text folderName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        QueryExecutionService service = SuperIR.getInstance().getQueryExecutionService();

        if (service != null) {
            if (!service.isRunning())
                service.restart();

            service.setOnSucceeded(event -> setup(service.getValue()));
            service.setOnFailed(event -> {
                // TODO
            });
        }
    }

    private void setup(QueryExecution queryExecution) {
        folderName.setText(queryExecution.getFolder().getName());
        queryField.setText(queryExecution.getQuery());

        for (Document document : queryExecution.getSorted()) {
            addDocumentPane(document, queryExecution.getScore(document));
        }
    }

    private void addDocumentPane(Document document, Score score) {
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setCursor(Cursor.HAND);
        vBox.setPadding(new Insets(10, 10, 10, 10));
        vBox.setSpacing(10);

        Text documentName = new Text(document.getFile().getName());
        Font font = new Font(18);
        documentName.setFont(font);

        long scoreValue = Math.round(score.getTotalScore() * 100);
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

    @FXML private void back(ActionEvent event) {
        new QueryInsertView().show();
    }
}
