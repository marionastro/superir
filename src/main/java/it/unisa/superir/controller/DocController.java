package it.unisa.superir.controller;

import it.unisa.superir.SuperIR;
import it.unisa.superir.model.Document;
import it.unisa.superir.service.QueryExecutionService;
import it.unisa.superir.view.FolderDocsView;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class DocController implements Initializable {
    @FXML private TextArea textArea;
    @FXML private TextField queryField;
    @FXML private Text fileName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        QueryExecutionService service = SuperIR.getInstance().getQueryExecutionService();
        Document selectedDocument = SuperIR.getInstance().getSelectedDocument();

        if (service.getState() == Worker.State.SUCCEEDED) {
            fileName.setText(selectedDocument.getFile().getName());
            queryField.setText(service.getValue().getQuery());
            textArea.appendText(selectedDocument.getTitle().getJoining());
            textArea.appendText("\n");
            textArea.appendText(selectedDocument.getBody().getJoining());
        } else {
            // TODO
        }
    }

    @FXML private void back(ActionEvent event) {
        new FolderDocsView().show();
    }
}
