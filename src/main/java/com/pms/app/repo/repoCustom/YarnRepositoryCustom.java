package com.pms.app.repo.repoCustom;

import com.pms.app.domain.Yarns;

/**
 * Created by arjun on 7/11/2017.
 */
public interface YarnRepositoryCustom extends AbstractRepositoryCustom<Yarns> {
    Yarns findByNameForImport(String yarnName);
}
