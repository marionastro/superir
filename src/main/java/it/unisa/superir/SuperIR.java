package it.unisa.superir;

import it.unisa.superir.algorithm.StandardIR;
import it.unisa.superir.algorithm.TFIDF;
import it.unisa.superir.math.CosineSimilarity;
import it.unisa.superir.model.Document;
import it.unisa.superir.model.Folder;
import it.unisa.superir.model.StopWordsFile;
import it.unisa.superir.model.Vocabulary;
import it.unisa.superir.view.DocView;
import it.unisa.superir.view.FolderSelectionView;
import javafx.application.Application;
import javafx.stage.Stage;

public class SuperIR extends Application {
    private static SuperIR instance;
    private Stage currentStage;

    @Override
    public void start(Stage primaryStage) {
        instance = this;
        currentStage = primaryStage;

        DocView view = new DocView();
        view.show();
    }

    private void test() throws Exception {

        Folder folder = new Folder();

        Document document = new Document("marco.txt");
        Document document2 = new Document("marco2.txt");

        StopWordsFile stopWordsFile = new StopWordsFile("marco3.txt");

        folder.add(document);
        folder.add(document2);
        folder.filter(stopWordsFile);

        Vocabulary tot = folder.getVocabulary();

        System.out.println(tot);

        String q = "Francesco è nato il 3 di settembre";

        TFIDF tfidf = new TFIDF(folder);
        StandardIR standardIR = new StandardIR();

        System.out.println("Query: " + q);

        double[] tfidf_queryValues = tfidf.getValues(q, tot);
        double[] tfidf_bodyDocumentValues = tfidf.getValues(document.getBody().getJoining(), tot);
        double[] tfidf_bodyDocument2Values = tfidf.getValues(document2.getBody().getJoining(), tot);
        double[] tfidf_titleDocumentValues = tfidf.getValues(document.getTitle().getJoining(), tot);
        double[] tfidf_titleDocument2Values = tfidf.getValues(document2.getTitle().getJoining(), tot);

        double[] std_queryValues = standardIR.getValues(q, tot);
        double[] std_bodyDocumentValues = standardIR.getValues(document.getBody().getJoining(), tot);
        double[] std_bodyDocument2Values = standardIR.getValues(document2.getBody().getJoining(), tot);
        double[] std_titleDocumentValues = standardIR.getValues(document.getTitle().getJoining(), tot);
        double[] std_titleDocument2Values = standardIR.getValues(document2.getTitle().getJoining(), tot);


        /*
            System.out.println(Arrays.toString(queryValues));
            System.out.println(Arrays.toString(documentValues));
            System.out.println(Arrays.toString(document2Values));
         */

        System.out.println("Similarità con TFIDF: ");

        double tfidf_s1 = CosineSimilarity.compute(tfidf_queryValues, tfidf_titleDocumentValues) * 0.7d
                + CosineSimilarity.compute(tfidf_queryValues, tfidf_bodyDocumentValues) * 0.3d;

        double tfidf_s2 = CosineSimilarity.compute(tfidf_queryValues, tfidf_titleDocument2Values) * 0.7d
                + CosineSimilarity.compute(tfidf_queryValues, tfidf_bodyDocument2Values) * 0.3d;

        System.out.println("1: " + tfidf_s1);
        System.out.println("2: " + tfidf_s2);

        System.out.println("Similarità con STD: ");

        double std_s1 = CosineSimilarity.compute(std_queryValues, std_titleDocumentValues) * 0.7d
                + CosineSimilarity.compute(std_queryValues, std_bodyDocumentValues) * 0.3d;

        double std_s2 = CosineSimilarity.compute(std_queryValues, std_titleDocument2Values) * 0.7d
                + CosineSimilarity.compute(std_queryValues, std_bodyDocument2Values) * 0.3d;

        System.out.println("1: " + std_s1);
        System.out.println("2: " + std_s2);

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

    public static SuperIR getInstance() {
        return instance;
    }

    public Stage getCurrentStage() {
        return currentStage;
    }
}
