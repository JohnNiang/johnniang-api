package me.johnniang.api.dto.base;

import me.johnniang.api.util.BeanUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.util.Assert;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Abstract output dto converter. It must be extended by DTO.
 *
 * @author johnniang
 * @date 11/27/18
 */
public abstract class AbstractOutputConverter<DTO, DOMAIN> implements OutputConverter<DTO, DOMAIN> {

    private final Class<DTO> dtoType = (Class<DTO>) fetchType(0);

    public AbstractOutputConverter() {
        Assert.isTrue(dtoType.equals(getClass()), "this converter must be extended by DTO type");
    }

    @Override
    public DTO convertFrom(DOMAIN domain) {
        BeanUtils.update(domain, this);
        return (DTO) this;
    }

    /**
     * Get actual generic type.
     *
     * @param index generic type index
     * @return real type will be returned
     */
    private Type fetchType(int index) {
        return ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[index];
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
    }
}
