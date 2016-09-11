package com.pms.app.service;

import com.pms.app.domain.Yarns;
import com.pms.app.schema.YarnDto;

import java.util.List;

public interface YarnService {
    List<Yarns> getYarns();

    Yarns addYarn(YarnDto yarnDto);

    Yarns getYarn(Long id);

    Yarns updateYarn(Long id, YarnDto yarnDto);


}
