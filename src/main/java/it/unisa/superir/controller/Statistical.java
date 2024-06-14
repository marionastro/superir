package it.unisa.superir.controller;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * A generic interface in which stats are showed.
 * @author Gruppo5
 */
@FunctionalInterface
public interface Statistical {

    /**
     *Function to obtain the pane containg statistics.
     * @return  the container of the stats.
     */
    Pane getStatsContainer();

    /**
     * Function to add a new stat on the view.
     * @param title     name of the stat.
     * @param data      value of the stat.
     * @param wrapData  tells if text is multiline or not.
     */
    default void addStats(String title, String data, boolean wrapData) {
        BorderPane rootPane = new BorderPane();
        VBox.setVgrow(rootPane, Priority.ALWAYS);
        rootPane.setPadding(new Insets(20, 20, 20, 20));
        rootPane.getStyleClass().add("statsContainer");

        Text titleText = new Text(title);
        titleText.setFont(new Font(24));
        BorderPane.setAlignment(titleText, Pos.TOP_LEFT);

        TextArea dataTextArea = new TextArea(data);
        dataTextArea.setWrapText(wrapData);
        dataTextArea.setPadding(new Insets(10, 0, 10, 0));
        dataTextArea.setEditable(false);
        dataTextArea.setPrefHeight(wrapData ? -1d : 0);
        dataTextArea.setPrefWidth(-1d);

        rootPane.setCenter(dataTextArea);
        rootPane.setTop(titleText);

        getStatsContainer().getChildren().add(rootPane);
    }
}
