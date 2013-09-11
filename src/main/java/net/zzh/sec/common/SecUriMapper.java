package net.zzh.sec.common;

import net.zzh.common.persistence.model.IEntity;
import net.zzh.common.web.IUriMapper;
import org.springframework.stereotype.Component;

@Component
public class SecUriMapper implements IUriMapper {

    public SecUriMapper() {
        super();
    }

    // API

    /**
     * - note: at this point, the URI is transformed into plural (added `s`) in a hardcoded way - this will change in the future
     */
    @Override
    public <T extends IEntity> String getUriBase(final Class<T> clazz) {
        return clazz.getSimpleName().toString().toLowerCase() + "s";
    }

}
