package com.pms.app.repo;

import com.pms.app.domain.WeavingWorkLog;
import com.pms.app.repo.repoCustom.WeavingWorkLogRepositoryCustom;

/**
 * Created by arjun on 7/11/2017.
 */
public interface WeavingWorkLogRepository extends AbstractRepository<WeavingWorkLog> ,WeavingWorkLogRepositoryCustom{
}
