package com.pms.app.service.impl;

import com.pms.app.domain.Sizes;
import com.pms.app.repo.PriceRepository;
import com.pms.app.repo.SizeRepository;
import com.pms.app.schema.SizeDto;
import com.pms.app.service.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SizeServiceImpl implements SizeService {

    private final SizeRepository sizeRepository;
    private final PriceRepository priceRepository;

    @Autowired
    public SizeServiceImpl(SizeRepository sizeRepository, PriceRepository priceRepository) {
        this.sizeRepository = sizeRepository;
        this.priceRepository = priceRepository;
    }

    @Override
    @Cacheable(cacheNames = "sizes")
    public List<Sizes> getSizes(Long designId, Long yarnId) {
        if(designId == null && yarnId == null){
            return  sizeRepository.findAllByOrderByNameAsc();
        }else {
           return  priceRepository.findSizesByDesignIdAndYarnId(designId,yarnId);
        }

    }

    @Override
    @CacheEvict(cacheNames = "sizes",allEntries = true)
    public Sizes addSize(SizeDto sizeDto) {
        Sizes duplicateSizeName = sizeRepository.findByName(sizeDto.getName());
        if (duplicateSizeName != null) {
            throw new RuntimeException("Size value already exists");
        }
        Sizes sizes = new Sizes();
        sizes.setName(sizeDto.getName());
        return sizeRepository.save(sizes);
    }

    @Override
    public Sizes getSize(Long id) {
        Sizes sizes = sizeRepository.findOne(id);
        if (sizes == null) {
            throw new RuntimeException(" Size not available");
        }
        return sizes;
    }

    @Override
    @CacheEvict(cacheNames = "sizes",allEntries = true)
    public Sizes updateSize(Long id, SizeDto sizeDto) {
        Sizes duplicateSizeName = sizeRepository.findByName(sizeDto.getName());
        if(duplicateSizeName!= null && duplicateSizeName.getId() != id) {
            throw new RuntimeException("Value already exists for another size");
        }
        Sizes sizes = sizeRepository.findOne(id);
        if (sizes == null) {
            throw new RuntimeException("No size available.");
        }
        sizes.setName(sizeDto.getName());
        return sizeRepository.save(sizes);
    }

}
