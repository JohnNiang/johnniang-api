package me.johnniang.api.dto.base;

/**
 * Converter interface for output DTO.
 *
 * @author johnniang
 * @date 11/27/18
 */
public interface OutputConverter<DTO, DOMAIN> {

    /**
     * Convert from domain.(shallow)
     *
     * @param domain domain data
     * @return converted dto data
     */
    DTO convertFrom(DOMAIN domain);
}
