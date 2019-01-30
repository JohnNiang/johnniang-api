package me.johnniang.api.dto.event;

import me.johnniang.api.dto.base.AbstractInputConverter;
import me.johnniang.api.entity.event.DeadlineEvent;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Objects;

/**
 * Deadline event intput dto.
 *
 * @author johnniang
 */
public class DeadlineEventInputDTO extends AbstractInputConverter<DeadlineEvent> {

    @NotBlank(message = "Name must not be blank")
    @Size(max = 50, message = "Name's length must not be more than {max}")
    private String name;

    @NotBlank(message = "Avatar must not be blank")
    @Size(max = 2048, message = "Avatar's length must not be more than {max}")
    private String avatar;

    @Future(message = "Dead time must be future")
    private Date deadTime;

    @Size(max = 1024, message = "Description's length must not be more than {max}")
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
        DeadlineEventInputDTO that = (DeadlineEventInputDTO) o;
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
