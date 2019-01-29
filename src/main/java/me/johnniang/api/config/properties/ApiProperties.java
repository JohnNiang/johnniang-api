package me.johnniang.api.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Api properties.
 *
 * @author johnniang
 */
@ConfigurationProperties("cn.johnniang.api")
public class ApiProperties {

    private boolean productionEnv = true;

    public boolean isProductionEnv() {
        return productionEnv;
    }

    public void setProductionEnv(boolean productionEnv) {
        this.productionEnv = productionEnv;
    }

}
