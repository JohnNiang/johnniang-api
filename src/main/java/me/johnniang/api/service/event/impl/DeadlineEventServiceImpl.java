package me.johnniang.api.service.event.impl;

import me.johnniang.api.entity.event.DeadlineEvent;
import me.johnniang.api.repository.event.DeadlineEventRepository;
import me.johnniang.api.service.event.DeadlineEventService;
import me.johnniang.api.service.base.AbstractService;
import org.springframework.stereotype.Service;

@Service
public class DeadlineEventServiceImpl extends AbstractService<DeadlineEvent, Integer> implements DeadlineEventService {

    private final DeadlineEventRepository deadlineEventRepository;

    public DeadlineEventServiceImpl(DeadlineEventRepository deadlineEventRepository) {
        super(deadlineEventRepository);
        this.deadlineEventRepository = deadlineEventRepository;
    }
}
