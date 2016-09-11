package com.pms.app.service;

import com.pms.app.domain.Prints;
import com.pms.app.schema.PrintDto;

import java.util.List;

public interface PrintService {
    List<Prints> getPrints();

    Prints addPrint(PrintDto printDto);

    Prints getPrint(Long id);

    Prints updatePrint(Long id, PrintDto printDto);

    List<Prints> getPrintsBySizeId(Long sizeId);
}
