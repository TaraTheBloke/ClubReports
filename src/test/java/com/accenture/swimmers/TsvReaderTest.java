package com.accenture.swimmers;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class TsvReaderTest {

    @Rule
    public TemporaryFolder directory = new TemporaryFolder();

    @Test
    public void shouldReadValidSwimmersFromFile() throws Exception {
        Path tsv = createTsvWith(swimmer("Zac", "Jones", "23/09/2005", "Minnows"),
                                 swimmer("Jack", "Smith", "03/10/2001", "Performance"),
                                 swimmer("Abe", "Thompson", "25/12/2009", "Dolphins"));

        Collection<Swimmer> swimmers = readSwimmersFrom(tsv);
        assertThat(swimmers.size(), is(3));
    }

    @Test
    public void shouldReadSwimmersFromFileInAlphabeticalOrder() throws Exception {
        Path tsv = createTsvWith(swimmer("Zac", "Jones", "23/09/2005", "Minnows"),
                                 swimmer("Jack", "Smith", "03/10/2001", "Performance"),
                                 swimmer("Abe", "Thompson", "25/12/2009", "Dolphins"));

        List<Swimmer> swimmers = readSwimmersFrom(tsv);
        assertThat(swimmers.get(0).getName(), is("Abe"));
        assertThat(swimmers.get(1).getName(), is("Jack"));
        assertThat(swimmers.get(2).getName(), is("Zac"));
    }

    @Test
    public void shouldReadSwimmersFromFileInAlphabeticalOrderIgnoringCase() throws Exception {
        Path tsv = createTsvWith(swimmer("Zac", "Jones", "23/09/2005", "Minnows"),
                                 swimmer("Jack", "Smith", "03/10/2001", "Performance"),
                                 swimmer("abe", "Thompson", "25/12/2009", "Dolphins"));

        List<Swimmer> swimmers = readSwimmersFrom(tsv);
        assertThat(swimmers.get(0).getName(), is("abe"));
        assertThat(swimmers.get(1).getName(), is("Jack"));
    }

    @Test
    public void shouldReadSwimmersFromFileOrderedByFirstNameThenSurname() throws Exception {
        Path tsv = createTsvWith(swimmer("abe", "Smyth", "23/09/2005", "Minnows"),
                                 swimmer("abe", "Jones", "03/10/2001", "Performance"),
                                 swimmer("abe", "Thompson", "25/12/2009", "Dolphins"));

        List<Swimmer> swimmers = readSwimmersFrom(tsv);
        assertThat(swimmers.get(0).toXml(), containsString("Jones"));
        assertThat(swimmers.get(1).toXml(), containsString("Smyth"));
        assertThat(swimmers.get(2).toXml(), containsString("Thompson"));
    }

    @Test
    public void shouldNotReadInvalidSwimmersFromFile() throws Exception {
        Path tsv = createTsvWith(swimmer("Zac", "Jones", "23/09/2005", "INJURED"),
                                 swimmer("Jack", "Smith", "03/10/2001", "LEFT"),
                                 swimmer("Abe", "Thompson", "25/12/2009", "Dolphins"));

        List<Swimmer> swimmers = readSwimmersFrom(tsv);
        assertThat(swimmers.size(), is(1));
        assertThat(swimmers.get(0).getName(), is("Abe"));
    }

    @Test
    public void shouldNotReadInvalidPathName() throws Exception {
        List<Swimmer> swimmers = readSwimmersFrom(Paths.get("invalid"));
        assertThat(swimmers.size(), is(0));
    }

    private List<Swimmer> readSwimmersFrom(Path tsv) {
        return TsvReader.swimmersIn(tsv.toString()).stream().collect(Collectors.toList());
    }

    private Path createTsvWith(String...lines) throws IOException {
        Path path = Paths.get(directory.newFile().toURI());
        ArrayList<String> allLines = new ArrayList<>();
        allLines.add("COLUMN HEADINGS");
        allLines.addAll(Arrays.asList(lines));
        return Files.write(path, allLines, StandardOpenOption.APPEND);
    }

    private String swimmer(String name, String surname, String dob, String squad) {
        return String.format("x\t surname\t name\t number\t email\t address1\t address2\t" +
                             "town\t postcode\t county\t details\t%s\t%s\t male\t%s\t%s",
                             name, surname, dob, squad);
    }
}
