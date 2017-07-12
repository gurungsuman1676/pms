package com.pms.app.repo.repoImpl;

import com.pms.app.domain.WeavingWorkLog;
import com.pms.app.repo.SizeRepository;
import com.pms.app.repo.WeavingWorkLogRepository;
import com.pms.app.repo.repoCustom.WeavingWorkLogRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

/**
 * Created by arjun on 7/11/2017.
 */
public class WeavingWorkLogRepositoryImpl extends AbstractRepositoryImpl<WeavingWorkLog, WeavingWorkLogRepository> implements WeavingWorkLogRepositoryCustom {
    public WeavingWorkLogRepositoryImpl() {
        super(WeavingWorkLog.class);
    }

    @Lazy
    @Autowired
    public void setRepository(WeavingWorkLogRepository repository) {
        this.repository = repository;
    }
}
