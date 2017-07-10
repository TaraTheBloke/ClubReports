package com.accenture.swimmers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.builder.ToStringBuilder;

class Family implements Comparable<Family>, Xmlable {

    private static final int LARGE_FAMILY_DISCOUNT = 100;

    private final String name;
    private final List<Swimmer> swimmers;
    private final double fees;
    private final double monthlyStandingOrder;
    private final String feesExplanation;

    public static Collection<Family> familiesOf(Collection<Swimmer> swimmers) {
        // Group by email rather last name as last names might not match
        Map<String, List<Swimmer>> families = swimmers.stream()
                                .collect(Collectors.groupingBy(Swimmer::getEmail));
        return families.values().stream()
                                .map(Family::new)
                                .sorted()
                                .collect(Collectors.toList());
    }

    private Family(List<Swimmer> swimmers) {
        this.name = swimmers.get(0).getParentSurname();
        this.swimmers = swimmers;
        this.fees = fees();
        this.feesExplanation = abbreviatedFeesExplanation();
        this.monthlyStandingOrder = monthlyStandingOrder();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public String toXml() {
        return new StringBuilder("<family>")
                           .append("<name>")
                           .append(name)
                           .append("</name>")
                           .append("<fees>")
                           .append(inPounds(fees))
                           .append("</fees>")
                           .append("<fees-explanation>")
                           .append(feesExplanation)
                           .append("</fees-explanation>")
                           .append("<standing-order>")
                           .append(inPounds(monthlyStandingOrder))
                           .append("</standing-order>")
                           .append("</family>")
                           .toString();
    }

    @Override
    public int compareTo(Family other) {
        return name.compareToIgnoreCase(other.name);
    }

    private double fees() {
        double fees = swimmers.stream().mapToDouble(Swimmer::fee).sum();
        if (hasLargeFamilyDiscount()) {
            fees -= LARGE_FAMILY_DISCOUNT;
        }
        return fees;
    }

    private static String inPounds(double value) {
        BigDecimal d = BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP);
        if (d.toString().endsWith("00")) {
            return String.valueOf(d.intValue());
        }
        return d.toString();
    }

    private boolean hasLargeFamilyDiscount() {
        return swimmers.size() >= 3;
    }

    private double monthlyStandingOrder() {
        return fees / 10;
    }

    private String abbreviatedFeesExplanation() {
        String explanation = swimmers.stream().map(swimmer -> inPounds(swimmer.fee()))
                                              .collect(Collectors.joining(" + "));
        if (hasLargeFamilyDiscount()) {
            return explanation + " - " + LARGE_FAMILY_DISCOUNT;
        }
        return explanation;
    }
}
