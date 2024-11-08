package asia.fourtitude.interviewq.jumble.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
// @JsonInclude(Include.NON_NULL)
public class GameGuessOutput {

    @Schema(
            title = "Result",
            description = "Result message.",
            example = "AnyOf[\"Guessed correctly\", \"Guessed incorrectly\", \"Guessed already\"]",
            requiredMode = RequiredMode.AUTO)
    private String result;

    @NotNull
    @Schema(
            title = "ID",
            description = "Unique identifier of the game state.",
            example = "4579256c-326f-4169-9b56-6d1d1a2c11f0",
            requiredMode = RequiredMode.AUTO)
    private String id;

    @NotNull
    @Schema(
            description = "Original word in game.",
            example = "tomato",
            minLength = 3,
            maxLength = 30,
            requiredMode = RequiredMode.AUTO)
    @JsonProperty(value = "original_word")
    @Size(min = 3, max = 30)
    private String originalWord;

    @NotNull
    @Schema(
            description = "Scramble letters of the word in game.",
            example = "amotto",
            minLength = 3,
            maxLength = 30,
            requiredMode = RequiredMode.AUTO)
    @JsonProperty(value = "scramble_word")
    @Size(min = 3, max = 30)
    private String scrambleWord;

    @Schema(
            description = "The word used in guessing play, if available.",
            example = "motto",
            minLength = 3,
            maxLength = 30,
            requiredMode = RequiredMode.AUTO)
    @JsonProperty(value = "guess_word")
    @Size(min = 3, max = 30)
    private String guessWord;

    @Schema(
            description = "The numbers of smaller/sub words, constructed using the letters from `original_word`.",
            example = "31",
            defaultValue = "0",
            requiredMode = RequiredMode.AUTO)
    @JsonProperty(value = "total_words")
    private int totalWords;

    @Schema(
            description = "The numbers of remaining smaller/sub words to guess.",
            example = "23",
            defaultValue = "0",
            requiredMode = RequiredMode.AUTO)
    @JsonProperty(value = "remaining_words")
    private int remainingWords;

    @Schema(
            description = "The list of words guessed correctly.",
            example = "EMPTY_LIST",
            requiredMode = RequiredMode.AUTO)
    @JsonProperty(value = "guessed_words")
    private List<String> guessedWords;

}
