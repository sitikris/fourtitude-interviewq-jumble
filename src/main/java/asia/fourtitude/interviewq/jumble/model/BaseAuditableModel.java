package asia.fourtitude.interviewq.jumble.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.ObjectUtils;

import java.util.Date;

@Setter
@Getter
@Builder
// @ToString(callSuper = true)
public class BaseAuditableModel {

    private String createdBy;
    private String updatedBy;

    private Date createdAt;
    private Date modifiedAt;

    public Date getCreatedAt() {
        return ObjectUtils.isEmpty(createdAt) ? new Date() : createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = ObjectUtils.isEmpty(createdAt) ? new Date() : createdAt;
    }
}
