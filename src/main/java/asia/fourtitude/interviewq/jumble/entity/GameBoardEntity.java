package asia.fourtitude.interviewq.jumble.entity;

import asia.fourtitude.interviewq.jumble.audit.BaseAuditable;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "board")
public class GameBoardEntity extends BaseAuditable {

    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Column(length = 36, nullable = false, updatable = false)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fk_state_id", nullable = false, insertable = false, updatable = false)
    @JsonBackReference
    private GameStateEntity state;

    @Column(name = "fk_state_id", length = 36, nullable = false)
    private String stateId;

    private String word;
}
