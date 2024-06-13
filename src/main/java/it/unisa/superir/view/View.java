package it.unisa.superir.view;

import it.unisa.superir.SuperIR;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Abstract interface that rappresents a generic view, providing fundamental methods.
 * @author Gruppo5
 */
public abstract class View {

    /**
     * Function to call when you need to substitude a scene with one that has
     * this type of view.
     */
    public void show() {
        try {
            Stage stage = SuperIR.getInstance().getCurrentStage();
            Parent sceneRoot = new FXMLLoader(getFXML()).load();
            Scene scene = stage.getScene();

            if (scene == null) {
                stage.setScene(new Scene(sceneRoot));
            } else {
                stage.getScene().setRoot(sceneRoot);
            }

            stage.setTitle(getTitle());
            stage.show();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.CLOSE);
            alert.showAndWait().ifPresent(c -> Platform.exit());
        }
    }

    /**
     * Funtion to run to obtaint the View FXML source.
     * @return      the View source.
     */
    public abstract URL getFXML();

    /**
     * Function to obtain the View title.
     * @return      the title of the view.
     */
    public abstract String getTitle();
}