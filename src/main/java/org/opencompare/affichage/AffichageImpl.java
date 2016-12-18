package org.opencompare.affichage;

import java.io.*;
import java.util.*;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.opencompare.Main;
import org.opencompare.analyse.Analyse;
import org.opencompare.traitement.Traitement;

/**
 * Created by Fonck on 07/11/2016.
 */
public class AffichageImpl implements Affichage {
    private final char INSECABLE = (char) 160;
    private final char UNDERSCORE = (char) 95;
    private final String NEW_LINE = System.getProperty("line.separator");
    private Analyse analyse;
    private Traitement traitement;

    public AffichageImpl(Traitement t, Analyse a) throws IOException {
        this.traitement = t;
        this.analyse = a;
    }

    public void HTMLGenerator(File parentFolder, String fileName) throws IOException {
        Map<String, Map<String, String>> data = traitement.getData();

        ArrayList<String> products = traitement.getProductListe();
        ArrayList<String> features = traitement.getFeatureListe();

        //Récuparation du contenu du fichier template
        InputStream inputTemplate = Main.class.getClass().getResourceAsStream("/templates/template.html");

        File htmlTemplateFile = new File(parentFolder + "/" + fileName + ".html");

        OutputStream outputStream = new FileOutputStream(htmlTemplateFile);
        IOUtils.copy(inputTemplate, outputStream);
        outputStream.close();

        String htmlString = FileUtils.readFileToString(htmlTemplateFile);
        htmlTemplateFile.delete();

        //Remplacement du titre
        htmlString = htmlString.replace("$title", fileName);

        String feature = "";
        for (int i = 1; i < features.size() + 1; i++) {
            feature = feature + "<option value=" + i + "> " + features.get(i - 1) + "</option>" + NEW_LINE;
        }

        htmlString = htmlString.replace("$feature", feature);
        String product = "";
        String writerjson = "";

        for (int i = 0; i < products.size(); i++) {
            product = product + "<li class=\"collection-item\">";
            Map temporaire = (Map) data.get(products.get(i));
            writerjson = writerjson + "\"" + products.get(i) + "\" : \n {";
            String ligne_html = "<span class='product_name present' ";

            for (int j = 0; j < features.size(); j++) {
                String nom_feature = features.get(j);
                String data_tmp = (String) temporaire.get(features.get(j));
                data_tmp = data_tmp.replace("'", " ").replace('"', ' ');

                if (j < features.size() - 1) {
                    writerjson = writerjson + "\"" + nom_feature + "\":\"" + data_tmp + "\", \n";
                } else {
                    writerjson = writerjson + "\"" + nom_feature + "\":" + data_tmp + "\n ";
                }
                nom_feature = nom_feature.toLowerCase().replace("'", "_").replace('"', '_').replace(INSECABLE, UNDERSCORE).replace(" ", "_").replace("/", "_par_");


                data_tmp = data_tmp.replace("'", "_").replace('"', '_').replace(INSECABLE, UNDERSCORE).replace(" ", "_");
                ligne_html = ligne_html + "data-" + nom_feature + "='" + data_tmp + "' ";
            }

            String produit = products.get(i);
            produit = produit.replace("'", "_").replace('"', '_').replace(INSECABLE, UNDERSCORE).replace(" ", "_");

            product = product + ligne_html + ">" + products.get(i) + "</span>" + NEW_LINE;
            product = product + getProductHtmlLine(produit);

            if (i < products.size() - 1) {
                writerjson = writerjson + "\n},";
            } else {
                writerjson = writerjson + "\n}";
            }
        }

        htmlString = htmlString.replace("$product", product);

        File file = new File(parentFolder, fileName + ".html");
        FileUtils.writeStringToFile(file, htmlString);
    }

    private String getProductHtmlLine(String product) {
        return "<a class=\"secondary-content\" data-name=" + product + " onClick=\"addProduct(this)\">\n" +
            "\t\t\t\t\t\t\t\t\t<i class=\"material-icons\">keyboard_arrow_right</i>\n" +
            "\t\t\t\t\t\t\t\t</a>\n" +
            "\t\t\t\t\t\t\t</li>";
    }
}

