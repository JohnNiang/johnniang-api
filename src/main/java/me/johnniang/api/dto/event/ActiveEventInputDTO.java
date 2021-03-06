package me.johnniang.api.dto.event;

import me.johnniang.api.dto.base.AbstractInputConverter;
import me.johnniang.api.entity.event.ActiveEvent;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Objects;

/**
 * Active event input dto.
 *
 * @author johnniang
 */
public class ActiveEventInputDTO extends AbstractInputConverter<ActiveEvent> {

    @NotBlank(message = "Name must not be blank")
    @Size(max = 50, message = "Name's length must not be more than {max}")
    private String name;

    @NotBlank(message = "Avatar must not be blank")
    @Size(max = 2048, message = "Avatar's length must not be more than {max}")
    private String avatar;

    @PastOrPresent(message = "Start time must not be future")
    private Date startTime;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActiveEventInputDTO that = (ActiveEventInputDTO) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(avatar, that.avatar) &&
                Objects.equals(startTime, that.startTime) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, avatar, startTime, description);
    }
}
