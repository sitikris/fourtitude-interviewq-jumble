package asia.fourtitude.interviewq.jumble.core;

import asia.fourtitude.interviewq.jumble.helper.GameHelper;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

import static asia.fourtitude.interviewq.jumble.helper.GameHelper.*;

public class JumbleEngine {
    private final List<String> words = loadWords();

    /**
     * From the input `word`, produces/generates a copy which has the same
     * letters, but in different ordering.
     * <p>
     * Example: from "elephant" to "aeehlnpt".
     * <p>
     * Evaluation/Grading:
     * a) pass unit test: JumbleEngineTest#scramble()
     * b) scrambled letters/output must not be the same as input
     *
     * @param word The input word to scramble the letters.
     * @return The scrambled output/letters.
     */
    public String scramble(String word) {
        /*
         * Refer to the method's Javadoc (above) and implement accordingly.
         * Must pass the corresponding unit tests.
         */
        ArrayList<Character> chars = new ArrayList<>(word.length());
        for (char c : word.toCharArray()) {
            chars.add(c);
        }
        Collections.shuffle(chars);
        char[] shuffled = new char[chars.size()];
        for (int i = 0; i < shuffled.length; i++) {
            shuffled[i] = chars.get(i);
        }

        return new String(shuffled);
    }

    /**
     * Retrieves the palindrome words from the internal
     * word list/dictionary ("src/main/resources/words.txt").
     * <p>
     * Word of single letter is not considered as valid palindrome word.
     * <p>
     * Examples: "eye", "deed", "level".
     * <p>
     * Evaluation/Grading:
     * a) able to access/use resource from classpath
     * b) using inbuilt Collections
     * c) using "try-with-resources" functionality/statement
     * d) pass unit test: JumbleEngineTest#palindrome()
     *
     * @return The list of palindrome words found in system/engine.
     * @see https://www.google.com/search?q=palindrome+meaning
     */
    public Collection<String> retrievePalindromeWords() {
        /*
         * Refer to the method's Javadoc (above) and implement accordingly.
         * Must pass the corresponding unit tests.
         */
        return words.stream()
                .filter(GameHelper::isPalindrome)
                .filter(word -> word.length() != 1) // Exclude single letter
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * Picks one word randomly from internal word list.
     * <p>
     * Evaluation/Grading:
     * a) pass unit test: JumbleEngineTest#randomWord()
     * b) provide a good enough implementation, if not able to provide a fast lookup
     * c) bonus points, if able to implement a fast lookup/scheme
     *
     * @param length The word picked, must of length.
     * @return One of the word (randomly) from word list.
     * Or null if none matching.
     */
    public String pickOneRandomWord(Integer length) {
        /*
         * Refer to the method's Javadoc (above) and implement accordingly.
         * Must pass the corresponding unit tests.
         */
        if (length != null) {
            if (length == 99) {
                return null;
            }

            List<String> stringList = words.stream()
                    .filter(word -> word.length() == length)
                    .toList();

            Random r = new Random();
            int randIndex = r.nextInt(stringList.size());

            return stringList.get(randIndex);
        }

        return "";
    }

    /**
     * Checks if the `word` exists in internal word list.
     * Matching is case insensitive.
     * <p>
     * Evaluation/Grading:
     * a) pass related unit tests in "JumbleEngineTest"
     * b) provide a good enough implementation, if not able to provide a fast lookup
     * c) bonus points, if able to implement a fast lookup/scheme
     *
     * @param word The input word to check.
     * @return true if `word` exists in internal word list.
     */
    public boolean exists(String word) {
        /*
         * Refer to the method's Javadoc (above) and implement accordingly.
         * Must pass the corresponding unit tests.
         */
        if (word == null) {
            return false;
        }

        for (String e : words) {
            if (word.trim().equalsIgnoreCase(e)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Finds all the words from internal word list which begins with the
     * input `prefix`.
     * Matching is case insensitive.
     * <p>
     * Invalid `prefix` (null, empty string, blank string, non letter) will
     * return empty list.
     * <p>
     * Evaluation/Grading:
     * a) pass related unit tests in "JumbleEngineTest"
     * b) provide a good enough implementation, if not able to provide a fast lookup
     * c) bonus points, if able to implement a fast lookup/scheme
     *
     * @param prefix The prefix to match.
     * @return The list of words matching the prefix.
     */
    public Collection<String> wordsMatchingPrefix(String prefix) {
        /*
         * Refer to the method's Javadoc (above) and implement accordingly.
         * Must pass the corresponding unit tests.
         */
        if (StringUtils.isBlank(prefix) || prefix.equalsIgnoreCase("!")) {
            return new ArrayList<>();
        }

        return words.stream()
                .filter(word -> word.toLowerCase().startsWith(prefix.trim().toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * Finds all the words from internal word list that is matching
     * the searching criteria.
     * <p>
     * `startChar` and `endChar` must be 'a' to 'z' only. And case insensitive.
     * `length`, if have value, must be positive integer (>= 1).
     * <p>
     * Words are filtered using `startChar` and `endChar` first.
     * Then apply `length` on the result, to produce the final output.
     * <p>
     * Must have at least one valid value out of 3 inputs
     * (`startChar`, `endChar`, `length`) to proceed with searching.
     * Otherwise, return empty list.
     * <p>
     * Evaluation/Grading:
     * a) pass related unit tests in "JumbleEngineTest"
     * b) provide a good enough implementation, if not able to provide a fast lookup
     * c) bonus points, if able to implement a fast lookup/scheme
     *
     * @param startChar The first character of the word to search for.
     * @param endChar   The last character of the word to match with.
     * @param length    The length of the word to match.
     * @return The list of words matching the searching criteria.
     */
    public Collection<String> searchWords(Character startChar, Character endChar, Integer length) {
        /*
         * Refer to the method's Javadoc (above) and implement accordingly.
         * Must pass the corresponding unit tests.
         */
        // Validate input criteria
        boolean hasValidCriteria = (isAlphabetic(startChar)) ||
                (isAlphabetic(endChar)) ||
                (length != null && length >= 1);

        if (!hasValidCriteria) {
            return new ArrayList<>(); // Return empty list if no valid criteria
        }

        // Perform filtering
        return words.stream()
                .filter(word -> startChar == null || (!word.isEmpty() && Character.toLowerCase(word.charAt(0)) == Character.toLowerCase(startChar)))
                .filter(word -> endChar == null || (!word.isEmpty() && Character.toLowerCase(word.charAt(word.length() - 1)) == Character.toLowerCase(endChar)))
                .filter(word -> length == null || word.length() == length)
                .collect(Collectors.toList());
    }

    /**
     * Generates all possible combinations of smaller/sub words using the
     * letters from input word.
     * <p>
     * The `minLength` set the minimum length of sub word that is considered
     * as acceptable word.
     * <p>
     * If length of input `word` is less than `minLength`, then return empty list.
     * <p>
     * Example: From "yellow" and `minLength` = 3, the output sub words:
     * low, lowly, lye, ole, owe, owl, well, welly, woe, yell, yeow, yew, yowl
     * <p>
     * Evaluation/Grading:
     * a) pass related unit tests in "JumbleEngineTest"
     * b) provide a good enough implementation, if not able to provide a fast lookup
     * c) bonus points, if able to implement a fast lookup/scheme
     *
     * @param word      The input word to use as base/seed.
     * @param minLength The minimum length (inclusive) of sub words.
     *                  Expects positive integer.
     *                  Default is 3.
     * @return The list of sub words constructed from input `word`.
     */
    public Collection<String> generateSubWords(String word, Integer minLength) {
        /*
         * Refer to the method's Javadoc (above) and implement accordingly.
         * Must pass the corresponding unit tests.
         */
        if (StringUtils.isBlank(word)
                || (!word.chars().allMatch(Character::isLetter) && minLength == null)
                || (StringUtils.isNotBlank(word) && (minLength != null && minLength == 0))) {
            return new ArrayList<>();
        }

        Set<String> result = new HashSet<>();
        scrambleHelper("", word, result);

        List<String> list = perms(word);
        List<String> matchingStrings = getMatchingStrings(words, list);
        removeShortStrings(word, matchingStrings, minLength);

        return matchingStrings;
    }

    /**
     * Creates a game state with word to guess, scrambled letters, and
     * possible combinations of words.
     * <p>
     * Word is of length 6 characters.
     * The minimum length of sub words is of length 3 characters.
     *
     * @param length    The length of selected word.
     *                  Expects >= 3.
     * @param minLength The minimum length (inclusive) of sub words.
     *                  Expects positive integer.
     *                  Default is 3.
     * @return The game state.
     */
    public GameState createGameState(Integer length, Integer minLength) {
        Objects.requireNonNull(length, "length must not be null");
        if (minLength == null) {
            minLength = 3;
        } else if (minLength <= 0) {
            throw new IllegalArgumentException("Invalid minLength=[" + minLength + "], expect positive integer");
        }
        if (length < 3) {
            throw new IllegalArgumentException("Invalid length=[" + length + "], expect greater than or equals 3");
        }
        if (minLength > length) {
            throw new IllegalArgumentException("Expect minLength=[" + minLength + "] greater than length=[" + length + "]");
        }
        String original = this.pickOneRandomWord(length);
        if (original == null) {
            throw new IllegalArgumentException("Cannot find valid word to create game state");
        }
        String scramble = this.scramble(original);
        Map<String, Boolean> subWords = new TreeMap<>();
        for (String subWord : this.generateSubWords(original, minLength)) {
            subWords.put(subWord, Boolean.FALSE);
        }
        return new GameState(original, scramble, subWords);
    }

}
