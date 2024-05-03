package algorithms.ts;

import java.util.List;
import java.util.Random;

abstract class MoveOperator {
    public abstract String apply(String pattern, List<String> choices);
}

class Remove extends MoveOperator {
    @Override
    public String apply(String pattern, List<String> choices) {
        Random random = new Random();
        StringBuilder patternBuilder = new StringBuilder(pattern);
        // Ensure at least one character remains
        int numRemovals = random.nextInt(patternBuilder.length());
        for (int i = 0; i < numRemovals; i++) {
            int toRemove = random.nextInt(patternBuilder.length());
            patternBuilder.deleteCharAt(toRemove);
        }
        return patternBuilder.toString();
    }
}

class Add extends MoveOperator {
    @Override
    public String apply(String pattern, List<String> choices) {
        Random random = new Random();
        StringBuilder patternBuilder = new StringBuilder(pattern);
        int numInserts = random.nextInt(pattern.length() + 1);
        for (int i = 0; i < numInserts; i++) {
            int toInsert = random.nextInt(patternBuilder.length() + 1);
            patternBuilder.insert(toInsert, choices.get(random.nextInt(choices.size())));
        }
        return patternBuilder.toString();
    }
}

class Change extends MoveOperator {
    @Override
    public String apply(String pattern, List<String> choices) {
        Random random = new Random();
        char[] chars = pattern.toCharArray();
        int numChanges = random.nextInt(chars.length + 1);
        for (int i = 0; i < numChanges; i++) {
            int toChange = random.nextInt(chars.length);
            chars[toChange] = choices.get(random.nextInt(choices.size())).charAt(0);
        }
        return new String(chars);
    }
}

class Swap extends MoveOperator {
    @Override
    public String apply(String pattern, List<String> choices) {
        Random random = new Random();
        char[] chars = pattern.toCharArray();
        int numSwaps = random.nextInt(chars.length);
        for (int i = 0; i < numSwaps; i++) {
            int idx1 = random.nextInt(chars.length);
            int idx2 = random.nextInt(chars.length);
            char temp = chars[idx1];
            chars[idx1] = chars[idx2];
            chars[idx2] = temp;
        }
        return new String(chars);
    }
}
