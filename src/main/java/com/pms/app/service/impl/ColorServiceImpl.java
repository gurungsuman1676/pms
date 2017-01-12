package com.pms.app.service.impl;

import com.pms.app.domain.Colors;
import com.pms.app.domain.Customers;
import com.pms.app.domain.Yarns;
import com.pms.app.repo.ColorRepository;
import com.pms.app.repo.YarnRepository;
import com.pms.app.schema.ColorDto;
import com.pms.app.service.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ColorServiceImpl implements ColorService {

    private final ColorRepository colorRepository;
    private final YarnRepository yarnRepository;

    @Autowired
    public ColorServiceImpl(ColorRepository colorRepository, YarnRepository yarnRepository) {
        this.colorRepository = colorRepository;
        this.yarnRepository = yarnRepository;
    }

    @Override
    public List<Colors> getColors() {
        return colorRepository.findAllByOrderByName_companyAsc();
    }

    @Override
    public Colors addColor(ColorDto colorDto) {

        if(colorDto.getYarnId() == null){
            throw new RuntimeException("Yarn is required");
        }

        Yarns yarns = yarnRepository.findOne(colorDto.getYarnId());
        if(yarns == null){
            throw new RuntimeException("No such yarns available");
        }
        Colors duplicateColors = colorRepository.findByName_companyAndYarnAndCode(colorDto.getName_company(), colorDto.getYarnId(), colorDto.getCode());
        if(duplicateColors != null){
            throw new RuntimeException("Color name already set for yarn");
        }

        Colors newColors = new Colors();
        newColors.setName_supplier(colorDto.getName_supplier());
        newColors.setName_company(colorDto.getName_company());
        newColors.setCode(colorDto.getCode());
        newColors.setYarn(yarns);
        return colorRepository.save(newColors);

    }

    @Override
    public Colors getColor(Long id) {

        Colors colors = colorRepository.findOne(id);
        if(colors == null){
            throw new RuntimeException("No such color available");
        }
        return colors;
    }

    @Override
    public Colors editColor(Long id, ColorDto colorDto) {

        Colors existingColor = colorRepository.findOne(id);
        if(existingColor == null){
            throw new RuntimeException("No such color available");
        }

        Yarns yarns = yarnRepository.findOne(colorDto.getYarnId());
        if(yarns == null){
            throw new RuntimeException("No such yarns available");
        }
        Colors duplicateColors = colorRepository.findByName_companyAndYarnAndCode(colorDto.getName_company(), colorDto.getYarnId(), colorDto.getCode());

        if(duplicateColors != null && !duplicateColors.getId().equals(id)){
            throw new RuntimeException("Color name already set for customer");
        }

//        existingColor.setName(colorDto.getName());
        existingColor.setName_supplier(colorDto.getName_supplier());
        existingColor.setName_company(colorDto.getName_company());
        existingColor.setCode(colorDto.getCode());
        existingColor.setYarn(yarns);
        return colorRepository.save(existingColor);
    }

    @Override
    public List<Colors> getColorsByYarnId(Long yarnId) {
        return colorRepository.findByYarn(yarnId);
    }
}
