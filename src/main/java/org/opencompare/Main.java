package org.opencompare;

/**
 * Created by Fonck on 29/11/2016.
 */
public class Main {
    public static void main(String[] args) {
        String[] menuItems = {"Lister les matrices du projet", "Générer une visualisation"};

        Menu menu = new Menu(menuItems);

        System.out.println("Bienvenue dans le menu du projet OpenCompare VIZ.");

        int choix = menu.gerer();

        while (choix != 0) {
            switch (choix) {
                case 1 :
                    System.out.println("Lister"); //Do something
                    break;
                case 2 :
                    System.out.println("Générer"); //Do something
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
}
