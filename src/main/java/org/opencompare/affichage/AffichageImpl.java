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
    private final char LINE_RETURN = (char) 10;
    private final String NEW_LINE = System.getProperty("line.separator");
    private Traitement traitement;
    private Analyse analyse;

    public AffichageImpl(Traitement t, Analyse a) {
        this.traitement = t;
        this.analyse = a;
    }

    /**
     * Recupere les graphique les plus pertinent des feature via le string donné en paramètre
     *
     * @param feature Nom de la feature
     * @return Une liste des graphiques des feature
     */
    private String getFeatureType(String feature) {
        String result = "";

        System.out.println("HERE : " + feature);

        if(analyse.isBinaire(feature)) {
            result = "binary";
        } else if(analyse.isDateFR(feature) || analyse.isDateUK(feature)) {
            result = "date";
        } else if(analyse.isPercent(feature)) {
            result = "percent";
        } else if(analyse.isInteger(feature)) {
            result = "integer";
        } else {
            result = "string";
        }

        return result;
    }

    /**
     * Genere les ressources ainsi que la visualisation nécessaire a l'utilisateur
     *
     * @param parentFolder Dossier contenant la PCM
     * @param fileName Nom du ficher PCM
     * @throws IOException
     */
    public void HTMLGenerator(File parentFolder, String fileName) throws IOException {
        Map<String, Map<String, String>> data = traitement.getData();

        List<String> products = traitement.getProductListe();
        List<String> features = traitement.getFeatureListe();

        for(String f : features) {
            System.out.println(f);
        }

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

        String htmlFeatures = "";
        for (int i = 1; i < features.size() + 1; i++) {
            String feature = features.get(i - 1);
            String featureValue = eraseSpecialCharacter(feature, true);
            htmlFeatures = htmlFeatures + "<option value=" + featureValue + " data-chart=" + getFeatureType(feature) + "> " + features.get(i - 1) + "</option>" + NEW_LINE;
        }

        htmlString = htmlString.replace("$feature", htmlFeatures);

        String htmlProducts = "";
        for (int i = 0; i < products.size(); i++) {
            htmlProducts = htmlProducts + "<li class=\"collection-item\">";
            Map productFeatures= (Map) data.get(products.get(i));
            String ligne_html = "<span class='product_name present' ";

            for (int j = 0; j < features.size(); j++) {
                String nom_feature = features.get(j);
                String data_tmp = (String) productFeatures.get(features.get(j));

                nom_feature = eraseSpecialCharacter(nom_feature, true);
                data_tmp = eraseSpecialCharacter(data_tmp, true);

                ligne_html = ligne_html + "data-" + nom_feature + "='" + data_tmp + "' ";
            }

            String produit = products.get(i);
            produit = eraseSpecialCharacter(produit, false);

            htmlProducts = htmlProducts + ligne_html + ">" + products.get(i) + "</span>" + NEW_LINE;
            htmlProducts = htmlProducts + getProductHtmlLine(produit);
        }

        htmlString = htmlString.replace("$product", htmlProducts);

        File file = new File(parentFolder, fileName + ".html");
        FileUtils.writeStringToFile(file, htmlString);
    }

    private String getProductHtmlLine(String product) {
        return "<a class=\"secondary-content\" data-name=" + product + " onClick=\"addProduct(this)\">\n" +
            "\t\t\t\t\t\t\t\t\t<i class=\"material-icons\">keyboard_arrow_right</i>\n" +
            "\t\t\t\t\t\t\t\t</a>\n" +
            "\t\t\t\t\t\t\t</li>";
    }

    private String eraseSpecialCharacter(String s, boolean lowerCase) {
        String res = s.replace("'", "_").replace('"', '_').replace(" ", "_").replace("/", "_par_").replace("-", "_").replace(LINE_RETURN, UNDERSCORE).replace(INSECABLE, UNDERSCORE);

        if (lowerCase) {
            return res.toLowerCase();
        } else {
            return res;
        }
    }
}

