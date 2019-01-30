package me.johnniang.api.controller.v1.event;

import io.swagger.annotations.ApiOperation;
import me.johnniang.api.dto.event.DeadlineEventOutputDTO;
import me.johnniang.api.service.event.DeadlineEventService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Deadline event controller.
 *
 * @author johnniang
 */
@RestController
@RequestMapping("/api/v1/events/deadline")
public class DeadlineEventController {

    private final DeadlineEventService deadlineEventService;

    public DeadlineEventController(DeadlineEventService deadlineEventService) {
        this.deadlineEventService = deadlineEventService;
    }

    @GetMapping
    @ApiOperation("Lists all deadline events")
    public List<DeadlineEventOutputDTO> listAll(@PageableDefault Pageable pageable) {
        return deadlineEventService.listAll(pageable)
                .stream()
                .map(deadlineEvent -> new DeadlineEventOutputDTO().convertFrom(deadlineEvent))
                .collect(Collectors.toList());
    }
}
