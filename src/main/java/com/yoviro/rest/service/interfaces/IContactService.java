package com.yoviro.rest.service.interfaces;

import com.yoviro.rest.dto.ContactDTO;
import com.yoviro.rest.dto.OfficialIdDTO;
import java.util.List;

public interface IContactService {
    public List<ContactDTO> findAll();
    public ContactDTO save(com.yoviro.rest.dto.ContactDTO contacto);
    public void delete(Long id);
    public ContactDTO findContactByOfficialId(OfficialIdDTO officialIdDTO);

}
