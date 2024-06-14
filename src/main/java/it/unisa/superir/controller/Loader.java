package it.unisa.superir.controller;

import javafx.scene.layout.Pane;

/**
 * A View that show a progress indicator because its content has to be calculated.
 * @author Gruppo5
 */
public interface Loader {

    /**
     * Function to run to obtain the loading pane.
     * @return  the loading pane.   
     */
    Pane getLoadingPane();

    /**
     * Function to run to obtain the content pane of the view.
     * @return the content pane.
     */
    Pane getContentPane();

    /**
     * Function to disable and show or not the loading pane.
     * @param loading true if the load pane has to be seen, false otherwise.
     */
    default void setLoading(boolean loading) {
        getLoadingPane().setDisable(!loading);
        getLoadingPane().setVisible(loading);
        getContentPane().setVisible(!loading);
        getContentPane().setDisable(loading);
    }
}
