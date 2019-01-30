package me.johnniang.api.controller.v1.event;

import io.swagger.annotations.ApiOperation;
import me.johnniang.api.dto.event.ActiveEventOutputDTO;
import me.johnniang.api.service.event.ActiveEventService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping
    @ApiOperation("Lists all active events")
    public List<ActiveEventOutputDTO> listAll() {
        return activeEventService.listAll()
                .stream()
                .map(activeEvent -> new ActiveEventOutputDTO().convertFrom(activeEvent))
                .collect(Collectors.toList());
    }

}
