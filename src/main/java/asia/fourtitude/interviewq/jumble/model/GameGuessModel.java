package asia.fourtitude.interviewq.jumble.model;

import asia.fourtitude.interviewq.jumble.core.GameState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GameGuessModel {

    private String id;

    private Date createdAt;

    private Date modifiedAt;

    private GameState gameState;

}
