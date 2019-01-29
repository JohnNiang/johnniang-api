package me.johnniang.api.service.impl;

import me.johnniang.api.entity.ActiveEvent;
import me.johnniang.api.repository.ActiveEventRepository;
import me.johnniang.api.service.ActiveEventService;
import me.johnniang.api.service.base.AbstractService;
import org.springframework.stereotype.Service;

/**
 * Active event service implementation.
 *
 * @author johnniang
 */
@Service
public class ActiveEventServiceImpl extends AbstractService<ActiveEvent, Integer> implements ActiveEventService {

    private final ActiveEventRepository activeEventRepository;

    public ActiveEventServiceImpl(ActiveEventRepository activeEventRepository) {
        super(activeEventRepository);
        this.activeEventRepository = activeEventRepository;
    }
}
