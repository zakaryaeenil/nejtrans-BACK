package com.teranil.nejtrans.web;

import com.teranil.nejtrans.model.dto.DossierDTO;
import com.teranil.nejtrans.model.dto.EventDTO;
import com.teranil.nejtrans.service.DossierService;
import com.teranil.nejtrans.service.EventService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
@RequestMapping("/api/events")
@Api(value = "Events operations", description = "Operations pertaining to events in Nejtrans Application")
public class EventController {

    private final EventService eventService;

    @ApiOperation(value = "Used by Admin to view a list of all events")
    @GetMapping("/all")
    public ResponseEntity<List<EventDTO>> getAll() {
        return eventService.getAll();
    }
}
