package com.pms.app.convert;

import com.pms.app.domain.Prints;
import com.pms.app.schema.PrintResource;

import java.util.List;

public interface PrintConvert {
    List<PrintResource> convert(List<Prints> prints);

    PrintResource convert(Prints prints);
}
