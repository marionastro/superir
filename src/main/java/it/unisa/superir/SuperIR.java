package it.unisa.superir;

import it.unisa.superir.model.Document;
import it.unisa.superir.service.FolderLoaderService;
import it.unisa.superir.service.QueryExecutionService;
import it.unisa.superir.view.FolderSelectionView;
import javafx.application.Application;
import javafx.stage.Stage;

public class SuperIR extends Application {
    private static SuperIR instance;
    private Stage currentStage;
    private FolderLoaderService folderLoaderService;
    private QueryExecutionService queryExecutionService;
    private Document selectedDocument;

    @Override
    public void start(Stage primaryStage) {
        instance = this;
        currentStage = primaryStage;

        currentStage.setWidth(1000);
        currentStage.setHeight(600);

        currentStage.setMinWidth(1000);
        currentStage.setMinHeight(600);

        FolderSelectionView view = new FolderSelectionView();
        view.show();
    }

    public static SuperIR getInstance() {
        return instance;
    }

    public Stage getCurrentStage() {
        return currentStage;
    }

    public FolderLoaderService getFolderLoaderService() {
        return folderLoaderService;
    }

    public void setFolderLoaderService(FolderLoaderService folderLoaderService) {
        this.folderLoaderService = folderLoaderService;
    }

    public QueryExecutionService getQueryExecutionService() {
        return queryExecutionService;
    }

    public void setQueryExecutionService(QueryExecutionService queryExecutionService) {
        this.queryExecutionService = queryExecutionService;
    }

    public Document getSelectedDocument() {
        return selectedDocument;
    }

    public void setSelectedDocument(Document selectedDocument) {
        this.selectedDocument = selectedDocument;
    }
}
