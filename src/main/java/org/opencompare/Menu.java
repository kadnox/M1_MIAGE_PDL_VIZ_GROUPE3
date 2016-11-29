package org.opencompare;

import java.util.Scanner;

/**
 * Created by Fonck on 29/11/2016.
 */
public class Menu {
    private String[] itemsMenu;

    public Menu(String[] itemsMenu) {
        this.itemsMenu = itemsMenu;
    }

    public int gerer() {
        Scanner scanner = new Scanner(System.in);
        int resultat;

        System.out.print("\n");

        for (int i = 0; i < this.itemsMenu.length; i++) {
            System.out.println(i + 1 + " - " + this.itemsMenu[i]);
        }

        System.out.println("0 - Quitter\n");
        int longueurMax = 0;

        do {
            System.out.print("Votre choix : ");

            String choix = scanner.next();
            longueurMax = this.itemsMenu.length;

            if (choix.matches("[0-9]+")) {
                if (Integer.parseInt(choix) > 0 || Integer.parseInt(choix) < longueurMax) {
                    resultat = Integer.parseInt(choix);
                } else {
                    resultat = -1;
                }
            } else {
                resultat = -1;
            }
        } while (resultat < 0 || resultat > longueurMax);

        return resultat;
    }
}
