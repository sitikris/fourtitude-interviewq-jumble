package asia.fourtitude.interviewq.jumble.model;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
// @JsonInclude(Include.NON_NULL)
public class GameGuessInput {

    @Schema(
            title = "ID",
            description = "Unique identifier of the game state.",
            example = "4579256c-326f-4169-9b56-6d1d1a2c11f0",
            nullable = false,
            requiredMode = RequiredMode.REQUIRED)
    @NotNull
    private String id;

    @Schema(
            title = "Word",
            description = "The word to guess.",
            example = "answer",
            minLength = 3,
            maxLength = 30,
            nullable = false,
            requiredMode = RequiredMode.REQUIRED)
    @NotBlank
    @Size(min = 3, max = 30)
    private String word;

}
