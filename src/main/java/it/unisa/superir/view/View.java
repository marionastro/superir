package it.unisa.superir.view;

import it.unisa.superir.SuperIR;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
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

            // Salva la dimensione e la posizione attuale della finestra
            double width = stage.getWidth();
            double height = stage.getHeight();
            double x = stage.getX();
            double y = stage.getY();

            // Carica la nuova scena
            Scene scene = new Scene(new FXMLLoader(getFXML()).load());

            stage.setTitle(getTitle());
            stage.setScene(scene);

            // Ripristina la dimensione e la posizione della finestra
            stage.setWidth(width);
            stage.setHeight(height);
            stage.setX(x);
            stage.setY(y);

            // CSS
            scene.getStylesheets().clear();
            scene.getStylesheets().add(getClass().getResource("/styles/light.css").toExternalForm());

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