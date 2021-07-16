package com.yoviro.rest.service.interfaces;

import com.yoviro.rest.dto.ContactDTO;
import com.yoviro.rest.models.entity.Contact;
import java.util.List;

public interface IContactService {
    public List<ContactDTO> findAll();
    public ContactDTO save(ContactDTO contactDTO);
    public void delete(Long id);
    public Contact findContactByOfficialId(String officialIDType, String officialIDNumber);
    public Contact getOrCreateContact(ContactDTO contactDTO);

}
