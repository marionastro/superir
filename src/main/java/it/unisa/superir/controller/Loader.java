package it.unisa.superir.controller;

import javafx.scene.layout.Pane;

public interface Loader {
    Pane getLoadingPane();

    Pane getContentPane();

    default void setLoading(boolean loading) {
        getLoadingPane().setDisable(!loading);
        getLoadingPane().setVisible(loading);
        getContentPane().setVisible(!loading);
        getContentPane().setDisable(loading);
    }
}
