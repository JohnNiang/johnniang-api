package me.johnniang.api.entity.event;

import me.johnniang.api.entity.base.BaseEntity;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * Active event entity.
 *
 * @author johnniang
 */
@Entity
@Table(name = "active_events")
@SQLDelete(sql = "update active_events set deleted = true where id = ?")
@Where(clause = "deleted = false")
public class ActiveEvent extends BaseEntity<Integer> {

    @Column(name = "name", columnDefinition = "varchar(50) not null")
    private String name;

    @Column(name = "avatar", columnDefinition = "varchar(2048) not null")
    private String avatar;

    @Column(name = "start_time", columnDefinition = "timestamp not null")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;

    @Column(name = "description", columnDefinition = "varchar(1024) default ''")
    private String description;

    @Column(name = "is_deactive", columnDefinition = "TINYINT default 0")
    private Boolean isDeactive;

    @Override
    protected void prePersist() {
        super.prePersist();

        if (StringUtils.isBlank(description)) {
            description = "";
        }

        if (isDeactive == null) {
            isDeactive = false;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getDeactive() {
        return isDeactive;
    }

    public void setDeactive(Boolean deactive) {
        isDeactive = deactive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActiveEvent that = (ActiveEvent) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(avatar, that.avatar) &&
                Objects.equals(startTime, that.startTime) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, avatar, startTime, description);
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
    }
}
