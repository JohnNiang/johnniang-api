package me.johnniang.api.controller.v1;

import me.johnniang.api.entity.ActiveEvent;
import me.johnniang.api.service.ActiveEventService;
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
@RequestMapping("/api/v1/active_events")
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
