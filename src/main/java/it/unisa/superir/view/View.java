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

public abstract class View {
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

    public abstract URL getFXML();

    public abstract String getTitle();
}