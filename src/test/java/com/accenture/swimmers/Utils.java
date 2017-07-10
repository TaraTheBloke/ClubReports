package com.accenture.swimmers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.accenture.swimmers.Swimmer.Builder;

public class Utils {

    static Swimmer.Builder swimmer() {
        return new Swimmer.Builder();
    }

    static List<Swimmer> listOf(Swimmer.Builder...builders) {
        return Arrays.asList(builders).stream()
                     .map(Builder::build)
                     .collect(Collectors.toList());
    }
}
