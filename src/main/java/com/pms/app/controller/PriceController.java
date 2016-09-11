package com.pms.app.controller;


import com.pms.app.convert.PriceConvert;
import com.pms.app.schema.PriceDto;
import com.pms.app.schema.PriceResource;
import com.pms.app.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = Routes.PRICE)
public class PriceController {

    private final PriceService priceService;
    private final PriceConvert priceConvert;

    private static final String PRICE_SEARCH =Routes.PRICE+"/search";

    @Autowired
    public PriceController(PriceService priceService, PriceConvert priceConvert) {
        this.priceService = priceService;
        this.priceConvert = priceConvert;
    }

    @RequestMapping(value="", method = RequestMethod.GET)
    public List<PriceResource> getPrices(){
        return priceConvert.convert(priceService.getPrices());
    }

    @RequestMapping(value = "",method = RequestMethod.POST)
    public PriceResource addPrice(@RequestBody PriceDto priceDto){
        return priceConvert.convert(priceService.addPrice(priceDto));
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public PriceResource getPrice(@PathVariable Long id){
        return priceConvert.convert(priceService.getPrice(id));
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public PriceResource updateDesign(@PathVariable Long id,@RequestBody PriceDto priceDto){
        return priceConvert.convert(priceService.updateDesign(id, priceDto));
    }

    @RequestMapping(value = PRICE_SEARCH,method = RequestMethod.GET)
    public PriceResource getPriceByDesignAndSizeAndColor(@RequestParam Long designId,
                                                         @RequestParam Long sizeId,
                                                         @RequestParam Long colorId){
        return priceConvert.convert(priceService.getPriceByDesignAndSizeAndColor(designId,sizeId,colorId));
    }
}
