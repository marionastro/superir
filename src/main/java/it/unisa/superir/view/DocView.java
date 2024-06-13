package it.unisa.superir.view;

import it.unisa.superir.SuperIR;

import java.net.URL;

/**
 *Class that rappresent the UI of the page that shows the content of a documents.
 * @author Gruppo5
 */
public class DocView extends View {

    /**
     *Funtion to run to obtaint the View FXML source.
     * @return      the View source.
     */
    @Override
    public URL getFXML() {
        return SuperIR.getInstance().getClass().getResource("/views/doc.fxml");
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
