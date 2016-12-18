package org.opencompare;

import org.apache.commons.io.*;
import org.opencompare.affichage.Affichage;
import org.opencompare.affichage.AffichageImpl;
import org.opencompare.traitement.Traitement;
import org.opencompare.traitement.TraitementImpl;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        String dir = System.getProperty("user.dir");

        if(args.length != 0) {
            if(args[0].equals("menu")) {
                String[] menuItems = {"Générer une visualisation"};

                Menu menu = new Menu(menuItems);

                System.out.println("Bienvenue dans le menu du projet OpenCompare VIZ.");
                int choix = menu.gerer();

                while (choix != 0) {
                    switch (choix) {
                        case 1 :
                            Scanner scanner = new Scanner(System.in);

                            System.out.print("Rentrer le chemin absolu de la pcm à visualiser : ");
                            String path = scanner.next();

                            boolean validPath = new File(path).exists();

                            if(validPath && path.contains(".pcm")) {
                                Traitement traitement = new TraitementImpl();
                                boolean isPCM = traitement.setData(new File(path));
                                boolean blankPCM = traitement.isPcmExploitableBlank();

                                if(isPCM && blankPCM) {
                                    String pathModified = path.replace("\\", "/");
                                    String[] pathSplit = pathModified.split("/");
                                    String title = pathSplit[pathSplit.length - 1].substring(0,  pathSplit[pathSplit.length - 1].length() - 4);

                                    Affichage aff = new AffichageImpl(traitement);

                                    File folderSource;
                                    //Recherche d'un nom de dossier
                                    if(new File(dir + "/" + title).exists()) {
                                        int i = 1;

                                        while(new File(dir + "/" + title + " (" + i + ")").exists()) {
                                            i++;
                                        }

                                        folderSource = new File(dir + "/" + title + " (" + i + ")");
                                    } else {
                                        folderSource = new File(dir + "/" + title);
                                    }

                                    //Dossier source des ressources
                                    File folderImg = new File(folderSource, "images");
                                    File folderCSS = new File(folderSource, "css");
                                    File folderJS = new File(folderSource, "js");

                                    //Création des dossiers
                                    if(folderSource.mkdir() && folderCSS.mkdir() && folderJS.mkdir() && folderImg.mkdir()) {
                                        //Copie des ressources dans les dossiers
                                        copyImageResources(folderImg);
                                        copyTemplateResources(folderCSS, folderJS);

                                        //Génération de la visualisation
                                        aff.HTMLGenerator(folderSource, title);
                                    } else {
                                        System.out.println("Echec de la génération du dossier.");
                                    }
                                } else {
                                    if(!isPCM) {
                                        System.out.println("Votre fichier n'est pas une PCM.");
                                    } else if(!blankPCM) {
                                        System.out.println("Votre PCM n'est pas exploitable.");
                                    }
                                }
                            } else {
                                System.out.println("Chemin inexistant.");
                            }

                            break;
                        default:
                            break;
                    }

                    choix = menu.gerer();
                }

                if (choix == 0) {
                    System.out.println("Merci d'avoir utiliser le projet OpenCompare VIZ.");
                }
            }
        } else {
            String os = System.getProperty("os.name").toLowerCase();

            if(os.contains("windows")) {
                Runtime.getRuntime().exec("cmd /c start cmd /k java -jar M1_MIAGE_PDL_VIZ_GROUPE3.jar menu");
            } else if(os.contains("mac")) {
                String[] cmd3 = {"xterm", "-e", "java -jar " + dir + "/M1_MIAGE_PDL_VIZ_GROUPE3.jar menu"};
                Process p3 = new ProcessBuilder(cmd3).start();

                Process p = new ProcessBuilder(
                        "gnome-terminal",
                        "-e",
                        "java -jar " + dir + "/M1_MIAGE_PDL_VIZ_GROUPE3.jar menu").start();

                Runtime.getRuntime().exec("/usr/bin/open -a Terminal java -jar M1_MIAGE_PDL_VIZ_GROUPE3.jar menu");
            } else if(os.contains("linux")) {
                Process p = new ProcessBuilder(
                        "gnome-terminal",
                        "-e",
                         "java -jar " + dir + "/M1_MIAGE_PDL_VIZ_GROUPE3.jar menu").start();
            }
        }
    }

    private static void copyImageResources(File folderImg) throws IOException {
        BufferedImage buff = ImageIO.read(Main.class.getClass().getResourceAsStream("/images/bar_chart.png"));
        ImageIO.write(buff, "png", new File(folderImg.getAbsolutePath() + "/bar_chart.png"));

        buff = ImageIO.read(Main.class.getClass().getResourceAsStream("/images/pie_chart.png"));
        ImageIO.write(buff, "png", new File(folderImg.getAbsolutePath() + "/pie_chart.png"));

        buff = ImageIO.read(Main.class.getClass().getResourceAsStream("/images/radar_chart.png"));
        ImageIO.write(buff, "png", new File(folderImg.getAbsolutePath() + "/radar_chart.png"));
    }

    private static void copyTemplateResources(File folderCSS, File folderJS) throws IOException {
        InputStream inputCss = Main.class.getClass().getResourceAsStream("/templates/css/style.css");
        FileWriter cssWriter = new FileWriter(folderCSS.getAbsolutePath() + "/style.css");
        cssWriter.write(IOUtils.toString(inputCss));
        cssWriter.close();

        InputStream inputChartJs = Main.class.getClass().getResourceAsStream("/templates/js/chart.js");
        FileWriter jsChartWriter = new FileWriter(folderJS.getAbsolutePath() + "/chart.js");
        jsChartWriter.write(IOUtils.toString(inputChartJs));
        jsChartWriter.close();

        InputStream inputUserJs = Main.class.getClass().getResourceAsStream("/templates/js/userConfig.js");
        FileWriter jsUserWriter = new FileWriter(folderJS.getAbsolutePath() + "/userConfig.js");
        jsUserWriter.write(IOUtils.toString(inputUserJs));
        jsUserWriter.close();
    }
}