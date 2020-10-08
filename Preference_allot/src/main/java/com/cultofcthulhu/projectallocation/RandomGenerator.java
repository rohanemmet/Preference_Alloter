package com.cultofcthulhu.projectallocation;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomGenerator {

    static List<String> words = Arrays.asList("Cthulhu", "Dagon", "worship", "mayhem", "society", "Miskatonic", "app", "research", "myths", "stories");

    public RandomGenerator () {}

    public static String generateString() {
        int numWords = ThreadLocalRandom.current().nextInt(1, words.size() - 1);
        StringBuilder sb = new StringBuilder();
        for(int i =0;i<numWords;i++) {
            int index = ThreadLocalRandom.current().nextInt(0, words.size());
            sb.append(words.get(index)).append(" ");
        }
        return sb.toString();
    }
}
