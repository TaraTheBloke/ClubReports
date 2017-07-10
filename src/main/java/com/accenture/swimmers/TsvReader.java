package com.accenture.swimmers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

class TsvReader {

    public static Collection<Swimmer> swimmersIn(String path) {
        try {
            return Files.lines(Paths.get(path))
                        .skip(1)
                        .filter(TsvReader::isValidLine)
                        .map(TsvReader::toSwimmer)
                        .sorted()
                        .collect(Collectors.toList());
        } catch (IOException e) {
            System.out.printf("Failed to read TSV file [%s]: %s\n", path, e.getMessage());
            return Collections.emptyList();
        }
    }

    private static boolean isValidLine(String line) {
        String squad = line.split("\t")[15];
        return Swimmer.isValidSquad(squad);
    }

    private static Swimmer toSwimmer(String line) {
        String[] parts = line.split("\t");
        return new Swimmer.Builder()
                    .withName(parts[11])
                    .withSurname(parts[12])
                    .withEmail(parts[4])
                    .withPhoneNumber(parts[3])
                    .withParentName(parts[2])
                    .withParentSurname(parts[1])
                    .withDob(parts[14])
                    .withSquad(parts[15])
                    .build();
    }
}
