package com.accenture.swimmers;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.endsWith;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class SwimmerTest {

    @Test
    public void shouldCreateSwimmerXml() throws Exception {
        Swimmer swimmer = new Swimmer.Builder()
                                .withName("Abe")
                                .withSurname("Jones")
                                .withDob("01/01/2001")
                                .withSquad("Performance")
                                .withEmail("test@blog.com")
                                .build();
        assertThat(swimmer.toXml(), startsWith("<swimmer><name>Abe,Jones</name><dob>01/01/2001</dob><squad>Performance</squad>"));
        assertThat(swimmer.toXml(), containsString("<email>test@blog.com</email>"));
        assertThat(swimmer.toXml(), endsWith("</swimmer>"));
    }

    @Test
    public void shouldVerifySquadIsValid() throws Exception {
        assertThat(Swimmer.isValidSquad("Affiliate"), is(true));
        assertThat(Swimmer.isValidSquad("Age Development"), is(true));
        assertThat(Swimmer.isValidSquad("Dolphins"), is(true));
        assertThat(Swimmer.isValidSquad("Junior Squad"), is(true));
        assertThat(Swimmer.isValidSquad("Masters"), is(true));
        assertThat(Swimmer.isValidSquad("Minnows"), is(true));
        assertThat(Swimmer.isValidSquad("Performance"), is(true));
        assertThat(Swimmer.isValidSquad("Student"), is(true));
        assertThat(Swimmer.isValidSquad("Youth"), is(true));
    }

    @Test
    public void shouldVerifySquadIsInvalid() throws Exception {
        assertThat(Swimmer.isValidSquad("LEFT"), is(false));
        assertThat(Swimmer.isValidSquad("Bla"), is(false));
    }
}
