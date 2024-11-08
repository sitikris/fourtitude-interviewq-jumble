package asia.fourtitude.interviewq.jumble.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;

@Getter
@Setter
public class PrefixForm {

    @NotNull
    @NotEmpty(message = "Word must not be blank")
    @Size(min = 3, max = 30, message = "size must be between 3 and 30")
    private String prefix;

    private Collection<String> words;

}
