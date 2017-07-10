package com.accenture.swimmers;

import java.util.Collection;

public class Main {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Main <fileName>");
            System.exit(0);
        }
        Collection<Swimmer> swimmers = TsvReader.swimmersIn(args[0]);
        PdfCreator.createDocuments(swimmers);
    }
}
