package asia.fourtitude.interviewq.jumble.model;

import asia.fourtitude.interviewq.jumble.core.GameState;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GameBoard {

    private String id;

    private GameState state;

    private String stateId;

    @NotNull
    @NotEmpty(message = "Word must not be blank")
    @Size(min = 3, max = 30, message = "size must be between 3 and 30")
    private String word;

    private String createdBy;
    private String lastModifiedBy;
}
