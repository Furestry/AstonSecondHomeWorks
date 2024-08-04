package ru.furestry.astonhomework.util;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Utils {
    public static String getParameterValue(String start, Map<String, String[]> map) {
        Optional<String> key = map.keySet().parallelStream()
                .filter(k -> k.startsWith(start))
                .findFirst();

        return key.isPresent() ? map.get(key.get())[0] : "0";
    }

    public static List<String> getParameterValues(String start, Map<String, String[]> map) {
        return map.keySet().parallelStream()
                .filter(k -> k.startsWith(start))
                .map(k -> map.get(k)[0])
                .toList();
    }
}
