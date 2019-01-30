package me.johnniang.api.dto.event;

import me.johnniang.api.dto.base.AbstractOutputConverter;
import me.johnniang.api.entity.event.ActiveEvent;

import java.util.Date;
import java.util.Objects;

/**
 * Active event output dto.
 *
 * @author johnniang
 */
public class ActiveEventOutputDTO extends AbstractOutputConverter<ActiveEventOutputDTO, ActiveEvent> {

    private Integer id;

    private String name;

    private String avatar;

    private Date startTime;

    private String description;

    private Boolean isDeactive;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        ActiveEventOutputDTO that = (ActiveEventOutputDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(avatar, that.avatar) &&
                Objects.equals(startTime, that.startTime) &&
                Objects.equals(description, that.description) &&
                Objects.equals(isDeactive, that.isDeactive);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, avatar, startTime, description, isDeactive);
    }
}
