package com.pms.app.controller;

import com.pms.app.convert.KnitterConvert;
import com.pms.app.schema.KnitterDto;
import com.pms.app.schema.KnitterResource;
import com.pms.app.service.KnitterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by arjun on 6/21/2017.
 */

@RestController
@RequestMapping(value = Routes.KNITTER)
public class KnitterController {
    private final KnitterConvert knitterConvert;
    private final KnitterService knitterService;

    @Autowired
    public KnitterController(KnitterConvert knitterConvert, KnitterService knitterService) {
        this.knitterConvert = knitterConvert;
        this.knitterService = knitterService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<KnitterResource> getLocation() {
        return knitterConvert.convert(knitterService.getAll());
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public KnitterResource addLocation(@RequestBody KnitterDto knitterDto) {
        return knitterConvert.convert(knitterService.add(knitterDto));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public KnitterResource getLocation(@PathVariable Long id) {
        return knitterConvert.convert(knitterService.get(id));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public KnitterResource editLocation(@PathVariable Long id, @RequestBody KnitterDto knitterDto) {
        return knitterConvert.convert(knitterService.edit(id, knitterDto));
    }
}
