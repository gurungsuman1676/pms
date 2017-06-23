package com.pms.app.service;

import com.pms.app.domain.Knitter;
import com.pms.app.schema.KnitterDto;

import java.util.List;

/**
 * Created by arjun on 6/21/2017.
 */
public interface KnitterService {
    List<Knitter> getAll();

    Knitter add(KnitterDto knitterDto);

    Knitter get(Long id);

    Knitter edit(Long id, KnitterDto knitterDto);
}
