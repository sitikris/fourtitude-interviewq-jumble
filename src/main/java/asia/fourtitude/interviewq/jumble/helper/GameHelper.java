package asia.fourtitude.interviewq.jumble.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class GameHelper {

    public static List<String> loadWords() {
        try {
            ClassPathResource resource = new ClassPathResource("words.txt");
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(resource.getInputStream())
            );
            return reader.lines().collect(Collectors.toList());
        } catch (Exception e) {
            return new ArrayList<>();
            // e.printStackTrace();
        }
    }

    public static boolean isPalindrome(String word) {
        String lowercaseWord = word.toLowerCase();
        return lowercaseWord.contentEquals(new StringBuilder(lowercaseWord).reverse());
    }

    public static boolean isAlphabetic(Character character) {
        return character != null && Character.isLetter(character) && Character.isLowerCase(Character.toLowerCase(character));
    }

    public static boolean containsDuplicate(int[] stack) {
        for (int i = 0; i < stack.length; i++) {
            for (int j = 0; j < stack.length; j++) {
                if (stack[i] == stack[j] && i != j) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void scrambleHelper(String prefix, String remaining, Set<String> result) {
        if (remaining.isEmpty()) {
            result.add(prefix); // Add completed permutation to the result set
        } else {
            for (int i = 0; i < remaining.length(); i++) {
                // Recur with new prefix and the remaining letters minus the current letter
                scrambleHelper(prefix + remaining.charAt(i),
                        remaining.substring(0, i) + remaining.substring(i + 1),
                        result);
            }
        }
    }

    public static List<String> getMatchingStrings(List<String> originalList, List<String> anotherList) {
        List<String> matchingList = new ArrayList<>(originalList);
        matchingList.retainAll(anotherList); // Retain only elements that are also in anotherList
        return matchingList;
    }

    public static void removeShortStrings(String input, List<String> strings, Integer minLength) {
        strings.removeIf(s -> ((minLength == null) ? s.length() < 3 : s.length() < minLength) || s.equalsIgnoreCase(input));
    }

    public static List<String> perms(String string) {
        List<String> result = new ArrayList<>();
        char[] values = string.toCharArray();
        for (int width = 1; width <= values.length; width++) { // for every length
            int[] stack = new int[width];
            for (int i = 0; i < stack.length; i++) { // start from a specific point without duplicates
                stack[i] = stack.length - i - 1;
            }
            int position = 0;
            while (position < width) {

                position = 0;
                StringBuilder sb = new StringBuilder();
                while (position < width) { // build the string
                    sb.append(values[stack[position]]);
                    position++;
                }
                result.add(sb.toString());
                position = 0;
                while (position < width) {
                    if (stack[position] < values.length - 1) {
                        stack[position]++;
                        if (!GameHelper.containsDuplicate(stack))
                            break;
                        else
                            position = 0;
                    } else {
                        stack[position] = 0;
                        position++;
                    }
                }
            }
        }
        return result;
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isValidUUID(final String input) {
        try {
            Pattern UUID_REGEX =
                    Pattern.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");
            return UUID_REGEX.matcher(input).matches();
        } catch (Exception e) {
            return false;
        }
    }
}
