package asia.fourtitude.interviewq.jumble.entity;

import asia.fourtitude.interviewq.jumble.audit.BaseAuditable;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Entity
@Table(name = "state")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameStateEntity extends BaseAuditable {

    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Column(length = 36, nullable = false, updatable = false)
    private String id;

    private String original;

    private String scramble;

    @ElementCollection
    @CollectionTable(name = "sub_words",
            joinColumns = @JoinColumn(name = "sub_word_id"))
    @MapKeyColumn(name = "word")
    @Column(name = "is_correct")
    private Map<String, Boolean> subWords = new HashMap<>();

    @OneToMany(mappedBy = "state")
    @JsonManagedReference
    private List<GameBoardEntity> boards;

}
