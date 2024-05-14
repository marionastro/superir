package it.unisa.superir.view;

import it.unisa.superir.SuperIR;

import java.net.URL;

public class FolderSelectionView extends View {
    @Override
    public URL getFXML() {
        return SuperIR.getInstance().getClass().getResource("views/folderselection.fxml");
    }

    @Override
    public int getWidth() {
        return 1280;
    }

    @Override
    public int getHeight() {
        return 720;
    }

    @Override
    public String getTitle() {
        return "SuperIR";
    }
}
