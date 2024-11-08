package asia.fourtitude.interviewq.jumble.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;

@Getter
@Setter
public class SearchForm {

    @NotEmpty(message = "Invalid startChar")
    @Size(min = 0, max = 1, message = "size must be between 0 and 1")
    private String startChar;

    @NotEmpty(message = "Invalid endChar")
    @Size(min = 0, max = 1, message = "size must be between 0 and 1")
    private String endChar;

    @NotNull(message = "Invalid length")
    private Integer length;

    private Collection<String> words;

}
