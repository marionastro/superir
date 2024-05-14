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
            Scene scene = new Scene(new FXMLLoader(getFXML()).load(), getWidth(), getHeight());
            stage.setWidth(getWidth());
            stage.setHeight(getHeight());
            stage.setTitle(getTitle());
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.CLOSE);
            alert.showAndWait().ifPresent(c -> Platform.exit());
        }
    }

    public abstract URL getFXML();

    public abstract int getWidth();

    public abstract int getHeight();

    public abstract String getTitle();
}