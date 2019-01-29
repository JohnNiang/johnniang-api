package me.johnniang.api.service.impl;

import me.johnniang.api.entity.DeadlineEvent;
import me.johnniang.api.repository.DeadlineEventRepository;
import me.johnniang.api.service.DeadlineEventService;
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
