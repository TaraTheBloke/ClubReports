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

public class SquadTest {

    @Test
    public void shouldConvertSwimmersToSingleSquad() throws Exception {
        List<Swimmer> swimmers = listOf(
            swimmer().withName("Abe").withSquad("Performance"),
            swimmer().withName("Zac").withSquad("Performance"));
        assertThat(Squad.squadsOf(swimmers).size(), is(1));
    }

    @Test
    public void shouldConvertSwimmersToMultipleSquads() throws Exception {
        List<Swimmer> swimmers = listOf(
            swimmer().withName("Abe").withSquad("Performance"),
            swimmer().withName("Zac").withSquad("Dolphins"),
            swimmer().withName("Ben").withSquad("Dolphins"));
        assertThat(Squad.squadsOf(swimmers).size(), is(2));
    }

    @Test
    public void shouldConvertSquadToXml() throws Exception {
        List<Swimmer> swimmers = listOf(
            swimmer().withName("Abe").withSquad("Performance"),
            swimmer().withName("Zac").withSquad("Dolphins"),
            swimmer().withName("Ben").withSquad("Dolphins"));
        String xml = Squad.squadsOf(swimmers)
                          .stream()
                          .collect(Collectors.toList())
                          .get(0)
                          .toXml();
        assertThat(xml, startsWith("<squad><name>Performance</name>"));
        assertThat(xml, containsString("<swimmers><swimmer><name>Abe"));
        assertThat(xml, endsWith("</swimmer></swimmers></squad>"));
    }
}
