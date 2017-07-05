package com.pms.app.service.impl;

import com.pms.app.domain.*;
import com.pms.app.repo.*;
import com.pms.app.schema.PriceDto;
import com.pms.app.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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
    @Cacheable(cacheNames = "prices")
    public List<Prices> getPrices() {
        return (List<Prices>) priceRepository.findAll();
    }

    @Override
    @CacheEvict(cacheNames = "prices",allEntries = true)
    public Prices addPrice(PriceDto priceDto) {

        if (priceDto.getSizeId() == null) {
            throw new RuntimeException("Size is required");
        }


        if (priceDto.getDesignId() == null) {
            throw new RuntimeException("Design is required");

        }

        Sizes sizes = sizeRepository.findOne(priceDto.getSizeId());
        if (sizes == null) {
            throw new RuntimeException("No such price found");
        }
        Designs designs = designRepository.findOne(priceDto.getDesignId());
        if (designs == null) {
            throw new RuntimeException("No such design found");
        }

        Prices duplicatePrices;
        if (priceDto.getYarnId() != null) {
            duplicatePrices = priceRepository.findByDesignAndSizeAndYarn(priceDto.getDesignId(), priceDto.getSizeId(), priceDto.getYarnId());
        } else {
            duplicatePrices = priceRepository.findByDesignAndSize(priceDto.getDesignId(), priceDto.getSizeId());
        }
        if (duplicatePrices != null) {
            throw new RuntimeException("Price already exists for design ,size and color");
        }
        Prices prices = new Prices();
        prices.setAmount(priceDto.getAmount());
        if (priceDto.getYarnId() != null) {
            Yarns yarns = yarnRepository.findOne(priceDto.getYarnId());
            if (yarns == null) {
                throw new RuntimeException("No such yarn found");
            }
            prices.setYarn(yarns);
        }
        prices.setDesign(designs);
        prices.setSize(sizes);
        return priceRepository.save(prices);
    }

    @Override
    @CacheEvict(cacheNames = "prices",allEntries = true)
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
        if (sizes == null) {
            throw new RuntimeException("No such price found");
        }
        Designs designs = designRepository.findOne(priceDto.getDesignId());
        if (designs == null) {
            throw new RuntimeException("No such design found");
        }
        Prices duplicatePrices;
        if (priceDto.getYarnId() != null) {
            duplicatePrices = priceRepository.findByDesignAndSizeAndYarn(priceDto.getDesignId(), priceDto.getSizeId(), priceDto.getYarnId());
        } else {
            duplicatePrices = priceRepository.findByDesignAndSize(priceDto.getDesignId(), priceDto.getSizeId());
        }
        if (duplicatePrices != null && !Objects.equals(duplicatePrices.getId(), id)) {
            throw new RuntimeException("Price already exists for design ,size and color");
        }
        existingPrice.setAmount(priceDto.getAmount());
        if (priceDto.getYarnId() != null) {
            Yarns yarns = yarnRepository.findOne(priceDto.getYarnId());
            if (yarns == null) {
                throw new RuntimeException("No such yarn found");
            }
            existingPrice.setYarn(yarns);
        } else {
            existingPrice.setYarn(null);
        }
        existingPrice.setDesign(designs);
        existingPrice.setSize(sizes);
        return priceRepository.save(existingPrice);
    }

    @Override
    public Prices getPriceByDesignAndSizeAndColor(Long designId, Long sizeId, Long colorId) {
        return priceRepository.findByDesignAndSizeAndYarn(designId, sizeId, colorId);
    }
}
