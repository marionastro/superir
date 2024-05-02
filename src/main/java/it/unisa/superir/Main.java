package it.unisa.superir;

import it.unisa.superir.data.Document;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.util.Arrays;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox vBox = new VBox();
        Text text = new Text("Hello World!");

        vBox.getChildren().add(text);
        Scene scene = new Scene(vBox);

        primaryStage.setWidth(600);
        primaryStage.setHeight(400);
        primaryStage.setScene(scene);
        primaryStage.show();

        Document document = new Document("marco.txt");

        document.getTitleVocabulary().printSorted();
        System.out.print("\n");
        document.getBodyVocabulary().printSorted();

        System.out.print("\n");
        System.out.println(Arrays.toString(document.getTitleValues()));

        System.out.print("\n");
        System.out.println(Arrays.toString(document.getBodyValues()));


        /*

        Il vocabolario sarà una Map<string, integer> con chiave la parola, e valore l'indice;
        Ogni parola verrà aggiunta alla mappa in indice crescente naturale

        se la parola è una stopword si fa un continue.

        si tiene conto anche delle parole del titolo

        ----

        Per generare il vettore dei valori di un documento
        Per ogni parola del documento
        int indice = voc.get(parole[0])
        \/
        valoriDoc[indice]++
        con valoriDoc un array di int lungo quanto il numero di parole uniche nel documento;

        Idem per la query.

        ---- TF-IDF ----

        Le query potrebbero essere un vettore di Coppie (Termine, Peso) dato che con TF-IDF
        ogni termine ha un peso diverso a differenza del classico algoritmo

        Questo potrebbe indicere sul vettore valoriDoc moltiplicando ogni valore per
        (pesoTermine * 10)

        ----

        Il titolo avrà un array valoriDoc tutto suo, che genererà uno score suo differente
        da quello del corpo

        Il totale dello score sarà:

        scoreTitolo * .7 + scoreCorpo * .3;

        (Magari si può decidere con uno slider ?)


        ----
        */
    }
}
