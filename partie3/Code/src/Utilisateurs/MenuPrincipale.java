package Utilisateurs;

import Utilisateurs.VoletAdmin.MVCAdmin.MenuAdmin;
import Utilisateurs.VoletClient.MVCClient.MenuClient;

import java.io.*;
import java.util.Scanner;

public class MenuPrincipale {

    public static void main(String[] args) throws FileNotFoundException {

        MenuAdmin vueAdmin = new MenuAdmin();
        MenuClient vueClient = new MenuClient();
        boolean printChoix = true;
        InputStream defaultIn = System.in;
        PrintStream dOut = System.out;
        String content = new Scanner(new File("src/input.txt")).useDelimiter("\\Z").next();
        System.setIn(new ByteArrayInputStream(content.getBytes()));
        System.setOut(new PrintStream(new OutputStream() {
            @Override
            public void write(int b) {
            }
        }));
        boolean firstExecution = true;
        MenuAdmin.scan = new Scanner(System.in);
        loop:
        while (true) {
            if (printChoix) {
                System.out.println(
                        """
                                Choisissez un menu a ouvrir selon votre statut, pour cela entrez un chiffre entre 1 et 2 pour sélectionner une des options suivantes,
                                ou 0 pour mettre fin au programme:
                                1. Menu Admin
                                2. Menu Client""");
            }

            String choix = MenuAdmin.scan.nextLine();
            switch (choix) {
                case "1" -> {
                    if (firstExecution) {

                        for (int i = 0; i < 3; i++) {
                            vueAdmin.ouvrirMenu();
                        }
                        firstExecution = false;
                        System.setIn(defaultIn);
                        MenuAdmin.scan = new Scanner(System.in);
                        MenuClient.scan = new Scanner(System.in);
                        System.setOut(dOut);
                    }
                    else {
                        vueAdmin.ouvrirMenu();
                        printChoix = true;
                    }
                }
                case "2" -> {
                    vueClient.ouvrirMenu();
                    printChoix = true;
                }
                case "0" -> {
                    break loop;
                }
                default -> {
                    System.out.println("Veuillez entrer uniquement un chiffre entre 1 et 2 svp");
                    printChoix = false;
                }
            }
        }
    }
}