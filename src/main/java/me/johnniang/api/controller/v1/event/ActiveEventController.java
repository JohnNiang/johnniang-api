package me.johnniang.api.controller.v1.event;

import me.johnniang.api.entity.event.ActiveEvent;
import me.johnniang.api.service.event.ActiveEventService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public List<ActiveEvent> listAll() {
        return activeEventService.listAll();
    }
}
