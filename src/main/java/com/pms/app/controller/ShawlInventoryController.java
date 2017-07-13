package com.pms.app.controller;

import com.pms.app.convert.ShawlInventoryConvert;
import com.pms.app.domain.ShawlInventory;
import com.pms.app.schema.ShawlInventoryBatchDetailResource;
import com.pms.app.schema.PageResult;
import com.pms.app.schema.ShawlInventoryDto;
import com.pms.app.schema.ShawlInventoryResource;
import com.pms.app.service.ShawlInventoryService;
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
 * Created by arjun on 6/27/2017.
 */

@RestController
@RequestMapping(value = Routes.SHAWL_ENTRY)
public class ShawlInventoryController {
    private final ShawlInventoryConvert shawlInventoryConvert;
    private final ShawlInventoryService shawlInventoryService;

    @Autowired
    public ShawlInventoryController(ShawlInventoryConvert shawlInventoryConvert, ShawlInventoryService shawlInventoryService) {
        this.shawlInventoryConvert = shawlInventoryConvert;
        this.shawlInventoryService = shawlInventoryService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public PageResult<ShawlInventoryResource> getShawlEntry(
            @RequestParam(value = "sizeId", required = false) Long sizeId,
            @RequestParam(value = "colorId", required = false) Long colorId,
            @RequestParam(value = "designId", required = false) Long designId,
            Pageable pageable) {
        Page<ShawlInventory> page = shawlInventoryService.getAll(sizeId, colorId, designId, pageable);
        return new PageResult<>(page.getTotalElements(), page.getSize(), page.getNumber(), shawlInventoryConvert.convert(page.getContent()));
    }

    @RequestMapping(method = RequestMethod.POST)
    public void updateShawlEntry(@RequestBody ShawlInventoryDto shawlInventoryDto) {
        shawlInventoryService.update(shawlInventoryDto);
    }

    @RequestMapping(value = "/batches/{batchId}",method = RequestMethod.GET)
    public ShawlInventoryDto getEntry(@PathVariable Long batchId) {
       return shawlInventoryService.get(batchId);
    }

    @RequestMapping(value = "/batches/{batchId}",method = RequestMethod.DELETE)
    public void deleteEntry(@PathVariable Long batchId) {
        shawlInventoryService.delete(batchId);
    }

    @RequestMapping(value = "/batches/{batchId}",method = RequestMethod.PUT)
    public void editEntry(@PathVariable Long batchId,@RequestBody ShawlInventoryDto shawlInventoryDto) {
        shawlInventoryService.edit(batchId,shawlInventoryDto);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/report")
    public void generateExcel(
            @RequestParam(value = "sizeId", required = false) Long sizeId,
            @RequestParam(value = "colorId", required = false) Long colorId,
            @RequestParam(value = "designId", required = false) Long designId,
            HttpServletResponse httpServletResponse) {
        shawlInventoryService.generateReport(sizeId, colorId, designId, httpServletResponse);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}/batches")
    public PageResult<ShawlInventoryBatchDetailResource> getItemBatchDetails(@PathVariable Long id,
                                                                             @RequestParam(required = false, value = "createdDateFrom") Date createdFrom,
                                                                             @RequestParam(required = false, value = "createdDateTo") Date createdTo,
                                                                             @RequestParam(required = false, value = "receiptNumber") String receiptNumber,
                                                                             Pageable pageable) {
        return shawlInventoryService.getBatchDetails(id, createdFrom, createdTo, receiptNumber,pageable);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}/batches/report")
    public void getItemBatchDetailsReport(@PathVariable Long id,
                                          @RequestParam(required = false, value = "createdDateFrom") Date createdFrom,
                                          @RequestParam(required = false, value = "createdDateTo") Date createdTo,
                                          @RequestParam(required = false, value = "receiptNumber") String receiptNumber,
                                          HttpServletResponse httpServletResponse) {
        shawlInventoryService.getBatchDetailsReport(id, createdFrom, createdTo,receiptNumber, httpServletResponse);
    }

}
