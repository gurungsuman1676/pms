package com.pms.app.convert.impl;

import com.pms.app.convert.PrintConvert;
import com.pms.app.domain.Prints;
import com.pms.app.schema.PrintResource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class PrintConvertImpl  implements PrintConvert {
    @Override
    public List<PrintResource> convert(List<Prints> prints) {
        return prints.stream().map(this::convert).collect(Collectors.toList());
    }

    @Override
    public PrintResource convert(Prints prints) {
        return PrintResource.builder()
                .id(prints.getId())
                .name(prints.getName())
                .sizeName(prints.getSize().getName())
                .sizeId(prints.getSize().getId())
                .amount(prints.getAmount())
                .currencyId(prints.getCurrency() != null ? prints.getCurrency().getId() : 0L)
                .currencyName(prints.getCurrency() != null ? prints.getCurrency().getName() : "N/A")
                .build();
    }
}
