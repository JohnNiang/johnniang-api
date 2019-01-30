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
@Table(name = "deadline_events")
@SQLDelete(sql = "update deadline_events set deleted = true where id = ?")
@Where(clause = "deleted = false")
public class DeadlineEvent extends BaseEntity<Integer> {

    @Column(name = "name", columnDefinition = "varchar(50) not null")
    private String name;

    @Column(name = "avatar", columnDefinition = "varchar(2048) not null")
    private String avatar;

    @Column(name = "dead_time", columnDefinition = "timestamp not null")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deadTime;

    @Column(name = "description", columnDefinition = "varchar(1024) not null default ''")
    private String description;

    @Override
    protected void prePersist() {
        super.prePersist();

        if (StringUtils.isBlank(description)) {
            description = "";
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

    public Date getDeadTime() {
        return deadTime;
    }

    public void setDeadTime(Date deadTime) {
        this.deadTime = deadTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DeadlineEvent that = (DeadlineEvent) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(avatar, that.avatar) &&
                Objects.equals(deadTime, that.deadTime) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, avatar, deadTime, description);
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
    }
}
