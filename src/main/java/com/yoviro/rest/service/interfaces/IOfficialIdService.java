package com.yoviro.rest.service.interfaces;
import com.yoviro.rest.models.entity.OfficialId;

import java.util.List;

public interface IOfficialIdService {
    public List<OfficialId> findAll();
    public void save(OfficialId officialId);
    public void delete(Long id);
}
