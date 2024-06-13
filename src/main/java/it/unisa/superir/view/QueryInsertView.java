package it.unisa.superir.view;

import it.unisa.superir.SuperIR;

import java.net.URL;

/**
 * Class that rappresent the UI of the Query Insertion page.
 * @author Gruppo5
 */
public class QueryInsertView extends View {

    /**
     *Funtion to run to obtaint the View FXML source.
     * @return      the View source.
     */
    @Override
    public URL getFXML() {
        return SuperIR.getInstance().getClass().getResource("/views/queryinsert.fxml");
    }

    /**
     *Function to obtain the View title.
     * @return      the title of the view.
     */
    @Override
    public String getTitle() {
        return "SuperIR";
    }
}
