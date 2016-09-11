package com.pms.app.controller;


import com.pms.app.convert.PrintConvert;
import com.pms.app.domain.Currency;
import com.pms.app.schema.PrintDto;
import com.pms.app.schema.PrintResource;
import com.pms.app.service.PrintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = Routes.PRINT)
public class PrintController {
    public static final String PRINT_SIZE_ID = "/sizes" + "/{sizeId}";
    private final PrintService printService;
    private final PrintConvert printConvert;


    @Autowired
    public PrintController(PrintService printService, PrintConvert printConvert) {
        this.printService = printService;
        this.printConvert = printConvert;
    }


    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<PrintResource> getPrints() {
        return printConvert.convert(printService.getPrints());
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public PrintResource addPrint(@RequestBody PrintDto printDto) {
        return printConvert.convert(printService.addPrint(printDto));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public PrintResource getPrint(@PathVariable Long id) {
        return printConvert.convert(printService.getPrint(id));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public PrintResource editPrint(@PathVariable Long id, @RequestBody PrintDto printDto) {
        return printConvert.convert(printService.updatePrint(id, printDto));
    }

    @RequestMapping(value = PRINT_SIZE_ID, method = RequestMethod.GET)
    public List<PrintResource> getPrintBySize(@PathVariable Long sizeId) {
        return printConvert.convert(printService.getPrintsBySizeId(sizeId));
    }

}
