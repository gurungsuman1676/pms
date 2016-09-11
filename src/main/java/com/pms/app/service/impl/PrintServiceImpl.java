package com.pms.app.service.impl;
import com.pms.app.domain.Currency;
import com.pms.app.domain.Prints;
import com.pms.app.domain.Sizes;
import com.pms.app.repo.CurrencyRepository;
import com.pms.app.repo.PrintRepository;
import com.pms.app.repo.SizeRepository;
import com.pms.app.schema.PrintDto;
import com.pms.app.service.PrintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PrintServiceImpl implements PrintService {

    private final PrintRepository printRepository;
    private final SizeRepository sizeRepository;
    private final CurrencyRepository currencyRepository;


    @Autowired
    public PrintServiceImpl(PrintRepository printRepository, SizeRepository sizeRepository, CurrencyRepository currencyRepository) {
        this.printRepository = printRepository;
        this.sizeRepository = sizeRepository;
        this.currencyRepository = currencyRepository;
    }

    @Override
    public List<Prints> getPrints() {
        return  printRepository.findAllByOrderByNameAsc();
    }

    @Override
    public Prints addPrint(PrintDto printDto) {

        Currency currency = currencyRepository.findOne(printDto.getCurrencyId());
        if(currency == null){
            throw new RuntimeException("No currency available");
        }

        if(printDto.getSizeId() == null){
            throw new RuntimeException("Size is required");
        }

        Sizes size = sizeRepository.findOne(printDto.getSizeId());
        if(size == null){
            throw new RuntimeException("Size not available");
        }
        Prints duplicatePrint= printRepository.findByNameAndSizeIdAndCurrencyId(printDto.getName(), printDto.getSizeId(), printDto.getCurrencyId());

        if(duplicatePrint != null){
            throw new RuntimeException("Print name with size already exists");
        }
        Prints newPrints = new Prints();
        newPrints.setCurrency(currency);
        newPrints.setName(printDto.getName());
        newPrints.setSize(size);
        newPrints.setAmount(printDto.getAmount());
        return printRepository.save(newPrints);
    }

    @Override
    public Prints getPrint(Long id) {
        Prints prints = printRepository.findOne(id);
    if(prints == null){
        throw new RuntimeException("No print found");
    }
        return  prints;
    }

    @Override
    public Prints updatePrint(Long id, PrintDto printDto) {

        Prints existingPrints = printRepository.findOne(id);

        if(existingPrints == null){
            throw new RuntimeException("No print available for update");
        }


        Sizes size = sizeRepository.findOne(printDto.getSizeId());
        if(size == null){
            throw new RuntimeException("Size not available");
        }

        Prints duplicatePrint= printRepository.findByNameAndSizeIdAndCurrencyId(printDto.getName(), printDto.getSizeId(),printDto.getCurrencyId());

        if(duplicatePrint != null && duplicatePrint.getId() != id){
            throw new RuntimeException("Print name with size already exists");
        }
        Currency currency =currencyRepository.findOne(printDto.getCurrencyId());
        if(currency == null){
            throw new RuntimeException("No currency available");
        }
        existingPrints.setName(printDto.getName());
        existingPrints.setSize(size);
        existingPrints.setAmount(printDto.getAmount());
        existingPrints.setCurrency(currency);
        return printRepository.save(existingPrints);
    }

    @Override
    public List<Prints> getPrintsBySizeId(Long sizeId) {
        return printRepository.findBySizeId(sizeId);
    }
}
