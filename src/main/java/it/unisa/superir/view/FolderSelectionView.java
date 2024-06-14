package it.unisa.superir.view;

import it.unisa.superir.SuperIR;

import java.net.URL;

/**
 * Class that represent the UI of the Folder Selection page.
 * @author Gruppo5
 */
public class FolderSelectionView extends View {

    /**
     * Function to run to obtain the View FXML source.
     * @return      the View source.
     */
    @Override
    public URL getFXML() {
        return SuperIR.getInstance().getClass().getResource("/views/folderselection.fxml");
    }

    /**
     * Function to obtain the View title.
     * @return      the title of the view.
     */
    @Override
    public String getTitle() {
        return "SuperIR";
    }
}
