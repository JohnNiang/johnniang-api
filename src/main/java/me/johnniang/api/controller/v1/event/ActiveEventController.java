package me.johnniang.api.controller.v1.event;

import io.swagger.annotations.ApiOperation;
import me.johnniang.api.dto.event.ActiveEventInputDTO;
import me.johnniang.api.dto.event.ActiveEventOutputDTO;
import me.johnniang.api.entity.event.ActiveEvent;
import me.johnniang.api.service.event.ActiveEventService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Active event controller.
 *
 * @author johnniang
 */
@RestController
@RequestMapping("/api/v1/events/activeline")
public class ActiveEventController {

    private final ActiveEventService activeEventService;

    public ActiveEventController(ActiveEventService activeEventService) {
        this.activeEventService = activeEventService;
    }

    @GetMapping("{activeEventId:\\d+}")
    @ApiOperation("Gets an active event")
    public ActiveEventOutputDTO get(@PathVariable("activeEventId") Integer activeEventId) {
        return new ActiveEventOutputDTO().convertFrom(activeEventService.getNonnullById(activeEventId));
    }

    @GetMapping
    @ApiOperation("Lists all active events")
    public List<ActiveEventOutputDTO> listAll(@PageableDefault Pageable pageable) {
        return activeEventService.listAll(pageable)
                .stream()
                .map(activeEvent -> new ActiveEventOutputDTO().convertFrom(activeEvent))
                .collect(Collectors.toList());
    }

    @PostMapping
    @ApiOperation("Creates an active events")
    public ActiveEventOutputDTO create(@RequestBody @Valid ActiveEventInputDTO activeEventInputDTO) {
        // Create event
        ActiveEvent activeEvent = activeEventService.create(activeEventInputDTO.convertTo());
        // Convert and return
        return new ActiveEventOutputDTO().convertFrom(activeEvent);
    }

    @PutMapping("{activeEventId:\\d+}")
    @ApiOperation("Updates an active events")
    public ActiveEventOutputDTO update(@RequestBody @Valid ActiveEventInputDTO activeEventInputDTO,
                                       @PathVariable("activeEventId") Integer activeEventId) {
        // Get active event by id
        ActiveEvent oldActiveEvent = activeEventService.getNonnullById(activeEventId);

        // Update properties
        activeEventInputDTO.update(oldActiveEvent);

        // Update to database, convert and return
        return new ActiveEventOutputDTO().convertFrom(activeEventService.update(oldActiveEvent));
    }

    @DeleteMapping("{activeEventId:\\d+}")
    @ApiOperation("Deletes an active events")
    public ActiveEventOutputDTO delete(@PathVariable("activeEventId") Integer activeEventId) {
        // Get active event
        ActiveEvent deletingActiveEvent = activeEventService.getNonnullById(activeEventId);

        // Remove it
        activeEventService.remove(deletingActiveEvent);

        // Convert and return
        return new ActiveEventOutputDTO().convertFrom(deletingActiveEvent);
    }
}
