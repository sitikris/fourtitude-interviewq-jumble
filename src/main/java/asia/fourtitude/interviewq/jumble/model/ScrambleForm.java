package asia.fourtitude.interviewq.jumble.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class ScrambleForm {

    @NotNull
    @NotEmpty(message = "Word must not be blank")
    @Size(min = 3, max = 30, message = "size must be between 3 and 30")
    private String word;

    private String scramble;
}
