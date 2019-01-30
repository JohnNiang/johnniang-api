package me.johnniang.api.dto.event;

import me.johnniang.api.dto.base.AbstractOutputConverter;
import me.johnniang.api.entity.event.DeadlineEvent;

import java.util.Date;
import java.util.Objects;

/**
 * Deadline event outupt dto.
 *
 * @author johnniang
 */
public class DeadlineEventOutputDTO extends AbstractOutputConverter<DeadlineEventOutputDTO, DeadlineEvent> {

    private String name;

    private String avatar;

    private Date deadTime;

    private String description;

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
        DeadlineEventOutputDTO that = (DeadlineEventOutputDTO) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(avatar, that.avatar) &&
                Objects.equals(deadTime, that.deadTime) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, avatar, deadTime, description);
    }

}
