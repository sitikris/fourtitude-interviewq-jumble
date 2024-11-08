package asia.fourtitude.interviewq.jumble.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public /* record */ class GameState {

    private String id;

    private String original;

    private String scramble;

    private String scrambleAsDisplay;

    private String word;

    private Map<String, Boolean> subWords;

    private String createdBy;
    private String lastModifiedBy;

    public GameState(String original, String scramble, Map<String, Boolean> subWords) {
        this.original = original;
        this.scramble = scramble;
        this.subWords = subWords;
    }

    public String getScrambleAsDisplay() {
        List<String> list = new ArrayList<>();
        for (char ch : this.scramble.toCharArray()) {
            list.add(Character.toString(ch));
        }
        return String.join(" ", list);
    }

    public List<String> getGuessedWords() {
        Map<Integer, Set<String>> guesseds = new TreeMap<>();
        for (Map.Entry<String, Boolean> entry : this.subWords.entrySet()) {
            if (entry.getValue() == Boolean.TRUE) {
                String word = entry.getKey();
                Integer len = word.length();
                Set<String> words = guesseds.computeIfAbsent(len, k -> new TreeSet<>());
                words.add(word);
            }
        }
        List<String> words = new ArrayList<>();
        for (Map.Entry<Integer, Set<String>> entry : guesseds.entrySet()) {
            words.addAll(entry.getValue());
        }
        return words;
    }

    public boolean updateGuessWord(String word) {
        if (word == null) {
            return false;
        }
        if (this.subWords.containsKey(word)) {
            this.subWords.put(word, Boolean.TRUE);
            return true;
        }
        return false;
    }

}
