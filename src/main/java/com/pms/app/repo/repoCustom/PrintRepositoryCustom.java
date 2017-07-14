package com.pms.app.repo.repoCustom;

import com.pms.app.domain.Prints;

import java.util.List;

/**
 * Created by arjun on 7/11/2017.
 */
public interface PrintRepositoryCustom extends AbstractRepositoryCustom<Prints> {

    List<Long> findByNameAndSizeId(String printName, Long sizeId);

    Long getDefaultPrintLessPrint();

}
