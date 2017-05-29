package com.example.app;

import java.util.Scanner;

/**
 * Created by nehajatav on 30/05/17.
 */
public class ConsoleApplication {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Press any key to ingest documents");
        if (sc.hasNext()) {
            System.out.println("Choose between the following options:");
            System.out.println("1. View text of a document");
            System.out.println("2. Remove noise from a document");
            System.out.println("3. Enrich a document with named entities");
            System.out.println("4. Enrich a document with sentiment");
            System.out.println("5. Proceed with these steps for all documents");
            while (sc.hasNext()) {
                try {
                    switch (Integer.parseInt(sc.nextLine())) {
                        case 1:
                            System.out.println("Enter filename to view text of the document");
                            //get text from elastic for sc.nextLine();
                            break;
                        case 2:
                            System.out.println("Enter filename to clean a document");
                            //remove noise for sc.nextLine();
                            break;
                        case 3:
                            System.out.println("Enter filename to enrich a document");
                            //do ner for sc.nextLine();
                            break;
                        case 4:
                            System.out.println("Enter filename to get sentiment of a document");
                            //get sentiment for sc.nextLine();
                            break;
                        default:
                            throw new NumberFormatException("Not a valid choice");
                    }
                } catch (NumberFormatException ex) {
                    System.err.println("Enter a valid choice between 1-4");
                }

            }
        }

        System.out.println("");
    }
}
