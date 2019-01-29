package me.johnniang.api.dto.base;

import me.johnniang.api.util.BeanUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Convenience for input dto.
 *
 * @author johnniang
 * @date 11/27/18
 */
public abstract class AbstractInputConverter<DOMAIN> implements InputConverter<DOMAIN> {

    private final Class<DOMAIN> domainType = (Class<DOMAIN>) fetchType(0);

    @Override
    public DOMAIN convertTo() {
        return BeanUtils.transfrom(domainType, this);
    }

    @Override
    public void update(DOMAIN domain) {
        BeanUtils.update(this, domain);
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
