package com.accenture.swimmers;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.builder.ToStringBuilder;

class Squad implements Xmlable {

    private final String name;
    private final Collection<Swimmer> swimmers;

    static Collection<Squad> squadsOf(Collection<Swimmer> swimmers) {
        Map<String, List<Swimmer>> squadMap = swimmers.stream()
                       .collect(Collectors.groupingBy(Swimmer::getSquad));
        return squadMap.entrySet().stream()
                       .map(e -> new Squad(e.getKey(), e.getValue()))
                       .collect(Collectors.toList());
    }

    private Squad(String name, Collection<Swimmer> swimmers) {
        this.name = name;
        this.swimmers = swimmers;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public String toXml() {
        StringBuilder xml = new StringBuilder("<squad><name>").append(name).append("</name><swimmers>");
        swimmers.forEach(swimmer -> xml.append(swimmer.toXml()));
        xml.append("</swimmers></squad>");
        return xml.toString();
    }
}
