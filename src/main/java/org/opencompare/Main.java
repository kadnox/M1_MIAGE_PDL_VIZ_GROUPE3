package org.opencompare;

import org.apache.commons.io.IOUtils;
import org.opencompare.affichage.Affichage;
import org.opencompare.affichage.AffichageImpl;
import org.opencompare.analyse.Analyse;
import org.opencompare.analyse.AnalyseImpl;
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
                                traitement.setDataInitial(new File(path));
                                boolean isPCM = traitement.setData(new File(path));
                                boolean isBlankPCM = traitement.isBlankPCM();

                                if(isPCM && !isBlankPCM) {
                                    generate(traitement, path, dir);
                                } else {
                                    if(!isPCM) {
                                        System.out.println("Votre fichier n'est pas une PCM.");
                                    } else if(isBlankPCM) {
                                        System.out.println("Votre PCM n'est pas exploitable.");

                                        String rep;
                                        do {
                                            System.out.println("Souhaitez vous quand même obtenir votre visualisation ? (o/n)");
                                            rep = scanner.next();
                                        } while(!rep.equals("o") && rep.equals("O") && rep.equals("n") && rep.equals("N"));

                                        if(rep.equals("o")) {
                                            do {
                                                System.out.println("Souhaitez vous obtenir votre visualisation réduite (Suppression des éléments inutiles) ? (o/n)");
                                                rep = scanner.next();
                                            } while(!rep.equals("o") && !rep.equals("O") && !rep.equals("n") && !rep.equals("N"));

                                            if(rep.equals("o") || rep.equals("O")) {
                                                traitement.removeUselessFeatures();
                                                traitement.removeUselessProduct();
                                                generate(traitement, path, dir);
                                            } else {
                                                generate(traitement, path, dir);
                                            }
                                        }
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
                Runtime.getRuntime().exec("/usr/bin/open -a Terminal java -jar " + dir + "/M1_MIAGE_PDL_VIZ_GROUPE3.jar menu");
            } else if(os.contains("linux")) {
                Process p = new ProcessBuilder(
                        "gnome-terminal",
                        "-e",
                         "java -jar " + dir + "/M1_MIAGE_PDL_VIZ_GROUPE3.jar menu").start();
            }
        }
    }


    private static void generate(Traitement traitement, String path, String dir) {
        String pathModified = path.replace("\\", "/");
        String[] pathSplit = pathModified.split("/");
        String title = pathSplit[pathSplit.length - 1].substring(0,  pathSplit[pathSplit.length - 1].length() - 4);

        Analyse analyse = new AnalyseImpl(traitement);
        Affichage aff = new AffichageImpl(traitement, analyse);

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
            try {
                //Copie des ressources dans les dossiers
                copyImageResources(folderImg);
                copyTemplateResources(folderCSS, folderJS);

                //Génération de la visualisation
                aff.HTMLGenerator(folderSource, title);
            } catch (IOException e) {
                System.out.println("Echec de la génération des dossiers de ressources.");
                folderSource.delete();
            }

            System.out.println("Visualisation généré.");
        } else {
            System.out.println("Echec de la génération du dossier.");
        }
    }

    private static void copyImageResources(File folderImg) throws IOException {
        BufferedImage buff = ImageIO.read(Main.class.getClass().getResourceAsStream("/images/bar_chart.png"));
        ImageIO.write(buff, "png", new File(folderImg.getAbsolutePath() + "/bar_chart.png"));

        buff = ImageIO.read(Main.class.getClass().getResourceAsStream("/images/pie_chart.png"));
        ImageIO.write(buff, "png", new File(folderImg.getAbsolutePath() + "/pie_chart.png"));

        buff = ImageIO.read(Main.class.getClass().getResourceAsStream("/images/radar_chart.png"));
        ImageIO.write(buff, "png", new File(folderImg.getAbsolutePath() + "/radar_chart.png"));

        buff = ImageIO.read(Main.class.getClass().getResourceAsStream("/images/polar_chart.png"));
        ImageIO.write(buff, "png", new File(folderImg.getAbsolutePath() + "/polar_chart.png"));

        buff = ImageIO.read(Main.class.getClass().getResourceAsStream("/images/line_chart.png"));
        ImageIO.write(buff, "png", new File(folderImg.getAbsolutePath() + "/line_chart.png"));
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