package com.yoviro.rest.service;

import com.yoviro.rest.dto.search.SearchContactDTO;
import com.yoviro.rest.models.repository.OfficialIdRepository;
import com.yoviro.rest.models.entity.OfficialId;
import com.yoviro.rest.models.repository.specification.handler.OperatorEnum;
import com.yoviro.rest.models.repository.specification.handler.SearchFilter;
import com.yoviro.rest.service.interfaces.IOfficialIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OfficialIdServiceImpl implements IOfficialIdService {
    @Autowired
    private OfficialIdRepository documentoDeIdentidadDao;

    @Override
    public List<OfficialId> findAll() {
        return (List<OfficialId>) documentoDeIdentidadDao.findAll();
    }

    @Override
    public void save(OfficialId officialId) {
        documentoDeIdentidadDao.save(officialId);
    }

    @Override
    public void delete(Long id) {
        documentoDeIdentidadDao.deleteById(id);
    }

    static List<SearchFilter> instanceContactSearchQry(SearchContactDTO searchContactDTO) {
        List<SearchFilter> filters = new ArrayList<>();
        SearchFilter officialIDFilter;
        if (searchContactDTO.getOfficialIDType() != null) {
            officialIDFilter = new SearchFilter();
            officialIDFilter.setProperty("officialIdType");
            officialIDFilter.setValue(searchContactDTO.getOfficialIDType());
            officialIDFilter.setOperator(OperatorEnum.EQUALS);

            filters.add(officialIDFilter);
        }

        if (searchContactDTO.getOfficialIDNumber() != null) {
            officialIDFilter = new SearchFilter();
            officialIDFilter.setProperty("officialIdNumber");
            officialIDFilter.setValue(searchContactDTO.getOfficialIDNumber());
            officialIDFilter.setOperator(OperatorEnum.EQUALS);

            filters.add(officialIDFilter);
        }
        return filters;
    }
}
