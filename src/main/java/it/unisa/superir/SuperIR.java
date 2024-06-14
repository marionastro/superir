package it.unisa.superir;

import it.unisa.superir.model.Document;
import it.unisa.superir.service.FolderLoaderService;
import it.unisa.superir.service.QueryExecutionService;
import it.unisa.superir.view.FolderSelectionView;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author Gruppo5
 */
public class SuperIR extends Application {
    private static SuperIR instance;
    private Stage currentStage;
    private FolderLoaderService folderLoaderService;
    private QueryExecutionService queryExecutionService;
    private Document selectedDocument;

    /**
     * Function to run to execute the application.
     * 
     * @param primaryStage the primary stage showed.
     */
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

    /**
     * Function to run to obtain the instance of an application.
     * 
     * @return an instance of the application.
     */
    public static SuperIR getInstance() {
        return instance;
    }

    /**
     * Function to run to obtain the current stage in use.
     * 
     * @return  the current stage used.
     */
    public Stage getCurrentStage() {
        return currentStage;
    }

    /**
     * Function to run to obtain the FolderLoaderService in use.
     * @return      the folderLoaderService used.
     */
    public FolderLoaderService getFolderLoaderService() {
        return folderLoaderService;
    }

    /**
     * Function to run to impose the new FolderLoaderService.
     * @param folderLoaderService   the folderLoaderService to use.
     */
    public void setFolderLoaderService(FolderLoaderService folderLoaderService) {
        this.folderLoaderService = folderLoaderService;
    }

    /**
     * Function to run to obtain the QueryExecutionService in use.
     * @return the queryExecutionService used.
     */
    public QueryExecutionService getQueryExecutionService() {
        return queryExecutionService;
    }

    /**
     * Function to run to impose the new QueryExecutionService.
     * @param queryExecutionService the queryExecutionService to use.
     */
    public void setQueryExecutionService(QueryExecutionService queryExecutionService) {
        this.queryExecutionService = queryExecutionService;
    }

    /**
     * Function to run to obtain the currently selected document.
     * @return the selected document.
     */
    public Document getSelectedDocument() {
        return selectedDocument;
    }

    /**
     * Function to run to impose which document is currently selected.
     * @param selectedDocument  a document to select.
     */
    public void setSelectedDocument(Document selectedDocument) {
        this.selectedDocument = selectedDocument;
    }
}
