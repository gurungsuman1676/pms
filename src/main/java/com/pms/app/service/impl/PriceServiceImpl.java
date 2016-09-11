package com.pms.app.service.impl;

import com.pms.app.domain.*;
import com.pms.app.repo.*;
import com.pms.app.schema.PriceDto;
import com.pms.app.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceServiceImpl implements PriceService {
    private final PriceRepository priceRepository;
    private final SizeRepository sizeRepository;
    private final DesignRepository designRepository;
private final YarnRepository yarnRepository;
    @Autowired
    public PriceServiceImpl(PriceRepository priceRepository, SizeRepository sizeRepository, DesignRepository designRepository, YarnRepository yarnRepository) {
        this.priceRepository = priceRepository;
        this.sizeRepository = sizeRepository;
        this.designRepository = designRepository;
        this.yarnRepository = yarnRepository;
    }


    @Override
    public List<Prices> getPrices() {
        return (List<Prices>) priceRepository.findAll();
    }

    @Override
    public Prices addPrice(PriceDto priceDto) {

        if(priceDto.getSizeId() == null){
            throw new RuntimeException("Size is required");
        }

        if(priceDto.getYarnId() == null){
            throw new RuntimeException("Color is required");

        }

        if(priceDto.getDesignId() == null){
            throw new RuntimeException("Design is required");

        }

        Sizes sizes = sizeRepository.findOne(priceDto.getSizeId());
        if(sizes == null){
            throw new RuntimeException("No such price found");
        }
        Designs designs = designRepository.findOne(priceDto.getDesignId());
        if(sizes == null){
            throw new RuntimeException("No such design found");
        }
        Yarns yarns = yarnRepository.findOne(priceDto.getYarnId());
        if(yarns == null){
            throw new RuntimeException("No such yarns available");
        }

        Prices duplicatePrices = priceRepository.findByDesignAndSizeAndYarn(priceDto.getDesignId(), priceDto.getSizeId(), priceDto.getYarnId());
        if(duplicatePrices != null){
            throw new RuntimeException("Price already exists for design ,size and color");
        }
        Prices prices = new Prices();
        prices.setAmount(priceDto.getAmount());
        prices.setYarn(yarns);
        prices.setDesign(designs);
        prices.setSize(sizes);
        return priceRepository.save(prices);
    }

    @Override
    public Prices getPrice(Long id) {
        Prices prices = priceRepository.findOne(id);
        if (prices == null) {
            throw new RuntimeException("No such price available");
        }
        return prices;
    }

    @Override
    public Prices updateDesign(Long id, PriceDto priceDto) {

        Prices existingPrice = priceRepository.findOne(id);
        if (existingPrice == null) {
            throw new RuntimeException("No such price available");
        }

        Sizes sizes = sizeRepository.findOne(priceDto.getSizeId());
        if(sizes == null){
            throw new RuntimeException("No such price found");
        }
        Designs designs = designRepository.findOne(priceDto.getDesignId());
        if(sizes == null){
            throw new RuntimeException("No such design found");
        }
        Yarns yarns = yarnRepository.findOne(priceDto.getYarnId());
        if(yarns == null){
            throw new RuntimeException("No such yarns available");
        }

        Prices duplicatePrices = priceRepository.findByDesignAndSizeAndYarn(priceDto.getDesignId(), priceDto.getSizeId(), priceDto.getYarnId());
        if(duplicatePrices != null && duplicatePrices.getId() != id){
            throw new RuntimeException("Price already exists for design ,size and color");
        }
        existingPrice.setAmount(priceDto.getAmount());
        existingPrice.setYarn(yarns);
        existingPrice.setDesign(designs);
        existingPrice.setSize(sizes);
        return priceRepository.save(existingPrice);
    }

    @Override
    public Prices getPriceByDesignAndSizeAndColor(Long designId, Long sizeId, Long colorId) {
        return priceRepository.findByDesignAndSizeAndYarn(designId, sizeId, colorId);
    }
}
