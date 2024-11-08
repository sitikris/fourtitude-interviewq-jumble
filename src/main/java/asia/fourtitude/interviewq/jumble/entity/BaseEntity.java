package asia.fourtitude.interviewq.jumble.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@MappedSuperclass
@ToString(callSuper = true)
public abstract class BaseEntity {

  // @Id
  // // @GeneratedValue(strategy = GenerationType.IDENTITY)
  // @Column(name = "id", nullable = false, unique = true)
  // private String id;

  @Column(name = "fk_createdBy", updatable = false)
  private Long createdBy;

  @CreatedDate
  @Temporal(TemporalType.TIMESTAMP)
  @Column(updatable = false)
  private Date createdDate  = new Date();

  @Column(name = "fk_modifiedBy")
  private Long modifiedBy;

  @LastModifiedDate
  @Temporal(TemporalType.TIMESTAMP)
  private Date modifiedDate = new Date();

  private Boolean active = Boolean.TRUE;

}
