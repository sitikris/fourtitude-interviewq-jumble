package asia.fourtitude.interviewq.jumble.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Getter
@Setter
public class SubWordsForm {

    @NotEmpty(message = "Invalid startChar")
    private String word;

    @NotNull(message = "Invalid length")
    private Integer minLength;

    private Collection<String> words;

}
