package it.unisa.superir.view;

import it.unisa.superir.SuperIR;

import java.net.URL;

public class QueryInsertView extends View {
    @Override
    public URL getFXML() {
        return SuperIR.getInstance().getClass().getResource("/views/queryinsert.fxml");
    }

    @Override
    public String getTitle() {
        return "SuperIR";
    }
}
