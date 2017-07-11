package com.pms.app.repo.repoCustom;

import com.pms.app.domain.Colors;

/**
 * Created by arjun on 7/11/2017.
 */
public interface ColorRepositoryCustom extends AbstractRepositoryCustom<Colors> {
    Colors findByCodeForImport(String colorCode);
}
