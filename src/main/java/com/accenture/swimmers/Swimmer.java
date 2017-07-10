package com.accenture.swimmers;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.google.common.collect.ImmutableMap;

class Swimmer implements Xmlable, Comparable<Swimmer> {

    private static final Map<String, Integer> SQUAD_FEES = ImmutableMap.<String, Integer>builder()
                                                            .put("Affiliate", 60)
                                                            .put("Age Development", 650)
                                                            .put("Dolphins", 400)
                                                            .put("Junior Squad", 600)
                                                            .put("Masters", 210)
                                                            .put("Minnows", 210)
                                                            .put("Performance", 730)
                                                            .put("Student", 220)
                                                            .put("Youth", 400)
                                                            .build();

    private final String name;
    private final String surname;
    private final String dob;
    private final String squad;
    private final String email;
    private final String phoneNumber;
    private final String parentName;
    private final String parentSurname;

    public static class Builder {

        private String name;
        private String surname;
        private String dob;
        private String squad;
        private String email;
        private String phoneNumber;
        private String parentName;
        private String parentSurname;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withSurname(String surname) {
            this.surname = surname;
            return this;
        }

        public Builder withDob(String dob) {
            this.dob = dob;
            return this;
        }

        public Builder withSquad(String squad) {
            this.squad = squad;
            return this;
        }

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder withPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder withParentName(String parentName) {
            this.parentName = parentName;
            return this;
        }

        public Builder withParentSurname(String parentSurname) {
            this.parentSurname = parentSurname;
            return this;
        }

        public Swimmer build() {
            return new Swimmer(name, surname, dob, squad, email, phoneNumber, parentName, parentSurname);
        }
    }

    public static boolean isValidSquad(String squad) {
        return SQUAD_FEES.keySet().contains(squad);
    }

    public int fee() {
        return SQUAD_FEES.get(squad);
    }

    private Swimmer(String name,
                    String surname,
                    String dob,
                    String squad,
                    String email,
                    String phoneNumber,
                    String parentName,
                    String parentSurname) {
        this.name = name;
        this.surname = surname;
        this.dob = dob;
        this.squad = squad;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.parentName = parentName;
        this.parentSurname = parentSurname;
    }

    public String getName() {
        return name;
    }

    public String getParentSurname() {
        return parentSurname;
    }

    public String getSquad() {
        return squad;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public String toXml() {
        String xml = "<swimmer>" +
                     "<name>%s</name>" +
                     "<dob>%s</dob>" +
                     "<squad>%s</squad>" +
                     "<email>%s</email>" +
                     "<phone>%s</phone>" +
                     "<parent>%s</parent>" +
                     "</swimmer>";
        return String.format(xml, name + "," + surname,
                                  dob,
                                  squad,
                                  getEmail(),
                                  phoneNumber,
                                  parentName + "," + parentSurname);
    }

    @Override
    public int compareTo(Swimmer other) {
        int compare = this.name.compareToIgnoreCase(other.name);
        if (compare == 0) {
            return this.surname.compareToIgnoreCase(other.surname);
        }
        return compare;
    }
}