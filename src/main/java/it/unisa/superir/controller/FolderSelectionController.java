package it.unisa.superir.controller;

import it.unisa.superir.SuperIR;
import it.unisa.superir.service.FolderLoaderService;
import it.unisa.superir.view.QueryInsertView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller used for interactions and updates on the FolderSelectionView.
 * @author Gruppo5
 */
public class FolderSelectionController implements Initializable {
    @FXML private AnchorPane rootPane;
    @FXML private Button selectFromComputerButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        rootPane.setOnDragOver(event -> {
            if (event.getDragboard().hasFiles()) {
                event.acceptTransferModes(TransferMode.ANY);
            }
        });

        rootPane.setOnDragDropped(event -> {
            File file = event.getDragboard().getFiles().get(0);

            if (file != null && file.isDirectory()) {
                SuperIR.getInstance().setFolderLoaderService(new FolderLoaderService(file));
                new QueryInsertView().show();
            }
        });

        selectFromComputerButton.setOnAction(event -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Seleziona la cartella");
            File file = directoryChooser.showDialog(SuperIR.getInstance().getCurrentStage());

            if (file != null && file.isDirectory()) {
                SuperIR.getInstance().setFolderLoaderService(new FolderLoaderService(file));
                new QueryInsertView().show();
            }
        });
    }
}
