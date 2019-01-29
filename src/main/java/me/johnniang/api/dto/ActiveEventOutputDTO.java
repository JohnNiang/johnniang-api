package me.johnniang.api.dto;

import me.johnniang.api.dto.base.AbstractOutputConverter;
import me.johnniang.api.entity.ActiveEvent;

import java.util.Date;

/**
 * Active event output dto.
 *
 * @author johnniang
 */
public class ActiveEventOutputDTO extends AbstractOutputConverter<ActiveEventOutputDTO, ActiveEvent> {

    private String name;

    private String avatar;

    private Date startTime;

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
}
