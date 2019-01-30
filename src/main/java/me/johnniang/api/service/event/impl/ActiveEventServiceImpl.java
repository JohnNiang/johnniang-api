package me.johnniang.api.service.event.impl;

import me.johnniang.api.entity.event.ActiveEvent;
import me.johnniang.api.repository.event.ActiveEventRepository;
import me.johnniang.api.service.event.ActiveEventService;
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
