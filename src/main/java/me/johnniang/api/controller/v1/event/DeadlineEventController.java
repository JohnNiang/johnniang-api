package me.johnniang.api.controller.v1.event;

import io.swagger.annotations.ApiOperation;
import me.johnniang.api.dto.event.DeadlineEventInputDTO;
import me.johnniang.api.dto.event.DeadlineEventOutputDTO;
import me.johnniang.api.entity.event.DeadlineEvent;
import me.johnniang.api.service.event.DeadlineEventService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @GetMapping("{deadlineEventId:\\d+}")
    @ApiOperation("Gets a deadline event")
    public DeadlineEventOutputDTO get(@PathVariable("deadlineEventId") Integer deadlineEventId) {
        return new DeadlineEventOutputDTO().convertFrom(deadlineEventService.getNonnullById(deadlineEventId));
    }

    @GetMapping
    @ApiOperation("Lists all deadline events")
    public List<DeadlineEventOutputDTO> listAll(@PageableDefault Pageable pageable) {
        return deadlineEventService.listAll(pageable)
                .stream()
                .map(deadlineEvent -> new DeadlineEventOutputDTO().convertFrom(deadlineEvent))
                .collect(Collectors.toList());
    }

    @PostMapping
    @ApiOperation("Creates a deadline event")
    public DeadlineEventOutputDTO create(@RequestBody @Valid DeadlineEventInputDTO deadlineEventInputDTO) {
        // Convert to deadline event
        DeadlineEvent deadlineEvent = deadlineEventInputDTO.convertTo();

        // Create deadline event
        return new DeadlineEventOutputDTO().convertFrom(deadlineEventService.create(deadlineEvent));
    }

    @PutMapping("{deadlineEventId:\\d+}")
    @ApiOperation("Updates a deadline event")
    public DeadlineEventOutputDTO update(@RequestBody @Valid DeadlineEventInputDTO deadlineEventInputDTO,
                                         @PathVariable("deadlineEventId") Integer deadlineEventId) {
        // Get old deadline event by id
        DeadlineEvent oldDeadlineEvent = deadlineEventService.getNonnullById(deadlineEventId);

        // Update properties
        deadlineEventInputDTO.update(oldDeadlineEvent);

        // Update to database, convert and return
        return new DeadlineEventOutputDTO().convertFrom(deadlineEventService.update(oldDeadlineEvent));
    }

    @DeleteMapping("{deadlineEventId:\\d+}")
    @ApiOperation("Deletes a deadline event")
    public DeadlineEventOutputDTO delete(@PathVariable("deadlineEventId") Integer deadlineEventId) {
        // Get old deadline event
        DeadlineEvent deletingDeadlineEvent = deadlineEventService.getNonnullById(deadlineEventId);

        // Remove it
        deadlineEventService.remove(deletingDeadlineEvent);

        // Convert and return
        return new DeadlineEventOutputDTO().convertFrom(deletingDeadlineEvent);
    }
}
