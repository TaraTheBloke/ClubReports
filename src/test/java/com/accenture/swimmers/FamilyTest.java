package com.accenture.swimmers;

import static com.accenture.swimmers.Utils.listOf;
import static com.accenture.swimmers.Utils.swimmer;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.endsWith;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

public class FamilyTest {

    @Test
    public void shouldConvertSwimmersToSingleFamily() throws Exception {
        List<Swimmer> swimmers = listOf(
            swimmer().withEmail("abe@me.com").withSquad("Performance"),
            swimmer().withEmail("abe@me.com").withSquad("Junior Squad"));
        assertThat(familiesOf(swimmers).size(), is(1));
    }

    @Test
    public void shouldConvertSwimmersToMultipleFamilies() throws Exception {
        List<Swimmer> swimmers = listOf(
            swimmer().withEmail("abe@me.com").withParentSurname("Smyth").withSquad("Performance"),
            swimmer().withEmail("zac@be.com").withParentSurname("Jones").withSquad("Minnows"),
            swimmer().withEmail("zac@be.com").withParentSurname("Jones").withSquad("Masters"));
        assertThat(familiesOf(swimmers).size(), is(2));
    }

    @Test
    public void shouldSortFamiliesAlphabeticallyIgnoringCase() throws Exception {
        List<Swimmer> swimmers = listOf(
            swimmer().withEmail("abe@me.com").withParentSurname("Smyth").withSquad("Performance"),
            swimmer().withEmail("ben@be.com").withParentSurname("Smith").withSquad("Minnows"),
            swimmer().withEmail("zac@be.com").withParentSurname("jones").withSquad("Masters"));
        assertThat(familiesOf(swimmers).get(0).toXml(), containsString("jones"));
        assertThat(familiesOf(swimmers).get(1).toXml(), containsString("Smith"));
        assertThat(familiesOf(swimmers).get(2).toXml(), containsString("Smyth"));
    }

    @Test
    public void shouldConvertFamilyToValueXml() throws Exception {
        List<Swimmer> swimmers = listOf(
            swimmer().withEmail("ab@me.com").withParentSurname("Smyth").withSquad("Performance"),
            swimmer().withEmail("zac@be.com").withParentSurname("Jones").withSquad("Masters"),
            swimmer().withEmail("zac@be.com").withParentSurname("Jones").withSquad("Youth"));
        String xml = familiesOf(swimmers).get(0).toXml();
        assertThat(xml, startsWith("<family><name>Jones</name>"));
        assertThat(xml, containsString("<fees>610</fees>"));
        assertThat(xml, containsString("<standing-order>61</standing-order>"));
        assertThat(xml, containsString("<fees-explanation>210 + 400</fees-explanation>"));
        assertThat(xml, endsWith("</family>"));
    }

    @Test
    public void shouldCalculateFeesForEachSquad() throws Exception {
        assertThat(xmlFor("Affiliate"), containsString("<fees>60</fees>"));
        assertThat(xmlFor("Age Development"), containsString("<fees>650</fees>"));
        assertThat(xmlFor("Dolphins"), containsString("<fees>400</fees>"));
        assertThat(xmlFor("Masters"), containsString("<fees>210</fees>"));
        assertThat(xmlFor("Minnows"), containsString("<fees>210</fees>"));
        assertThat(xmlFor("Junior Squad"), containsString("<fees>600</fees>"));
        assertThat(xmlFor("Performance"), containsString("<fees>730</fees>"));
        assertThat(xmlFor("Student"), containsString("<fees>220</fees>"));
        assertThat(xmlFor("Youth"), containsString("<fees>400</fees>"));
    }

    private static String xmlFor(String squad) {
        List<Swimmer> swimmers = listOf(swimmer().withEmail("ab@me.com")
                                                 .withParentSurname("Smyth")
                                                 .withSquad(squad));
        return familiesOf(swimmers).get(0).toXml();
    }

    private static List<Family> familiesOf(List<Swimmer> swimmers) {
        return Family.familiesOf(swimmers).stream().collect(Collectors.toList());
    }
}
