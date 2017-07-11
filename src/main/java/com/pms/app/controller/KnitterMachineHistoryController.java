package com.pms.app.controller;

import com.pms.app.convert.KnitterMachineHistoryConvert;
import com.pms.app.domain.KnitterMachineHistory;
import com.pms.app.schema.KnitterMachineHistoryDto;
import com.pms.app.schema.KnitterMachineHistoryResource;
import com.pms.app.schema.PageResult;
import com.pms.app.service.KnitterMachineHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by arjun on 6/21/2017.
 */

@RestController
@RequestMapping(Routes.KNITTER_HISTORY)
public class KnitterMachineHistoryController {

    private final KnitterMachineHistoryConvert knitterMachineHistoryConvert;
    private final KnitterMachineHistoryService knitterMachineHistoryService;

    @Autowired
    public KnitterMachineHistoryController(KnitterMachineHistoryConvert knitterMachineHistoryConvert, KnitterMachineHistoryService knitterMachineHistoryService) {
        this.knitterMachineHistoryConvert = knitterMachineHistoryConvert;
        this.knitterMachineHistoryService = knitterMachineHistoryService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public PageResult<KnitterMachineHistoryResource> getAllHistory(
            @RequestParam(required = false) Long knitterId,
            @RequestParam(required = false) Long machineId,
            @RequestParam(required = false) Date completedDate,
            @RequestParam(required = false) Date dateFrom,
            @RequestParam(required = false) Date dateTo,

            Pageable pageable) {
        Page<KnitterMachineHistory> page = knitterMachineHistoryService.getAll(knitterId, machineId, completedDate,dateFrom,dateTo, pageable);
        return new PageResult<>(page.getTotalElements(), page.getSize(), page.getNumber(), knitterMachineHistoryConvert.convert(page.getContent()));

    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public void addHistory(@RequestBody KnitterMachineHistoryDto knitterMachineHistoryDto) {
        knitterMachineHistoryService.add(knitterMachineHistoryDto);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteHistory(@PathVariable Long id) {
        knitterMachineHistoryService.delete(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void deleteHistory(@PathVariable Long id, @RequestBody KnitterMachineHistoryDto knitterMachineHistoryDto) {
        knitterMachineHistoryService.edit(id,knitterMachineHistoryDto);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public KnitterMachineHistoryDto getHistory(@PathVariable Long id) {
       return knitterMachineHistoryService.get(id);
    }


    @RequestMapping(value = "/report", method = RequestMethod.GET)
    public void getAllHistoryReport(
            @RequestParam(required = false) Long knitterId,
            @RequestParam(required = false) Long machineId,
            @RequestParam(required = false) Date completedDate,
            @RequestParam(required = false) Date dateFrom,
            @RequestParam(required = false) Date dateTo,
            HttpServletResponse httpServletResponse) {
        knitterMachineHistoryService.getHistoryReport(knitterId, machineId, completedDate, dateFrom,dateTo,httpServletResponse);

    }
}
