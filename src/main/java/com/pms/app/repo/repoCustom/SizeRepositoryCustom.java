package com.pms.app.repo.repoCustom;

import com.pms.app.domain.Sizes;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by arjun on 7/11/2017.
 */
public interface SizeRepositoryCustom extends AbstractRepositoryCustom<Sizes> {

    Long findIdByName(String sizeName);
}

