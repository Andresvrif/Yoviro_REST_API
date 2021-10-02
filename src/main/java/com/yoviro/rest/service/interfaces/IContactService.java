package com.yoviro.rest.service.interfaces;

import com.yoviro.rest.config.enums.OfficialIdTypeEnum;
import com.yoviro.rest.dto.ContactDTO;
import com.yoviro.rest.dto.search.SearchContactDTO;
import com.yoviro.rest.models.entity.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface IContactService {
    public List<ContactDTO> findAll();
    public Page<Contact> search(Pageable pageable, SearchContactDTO searchContactDTO);
    public ContactDTO save(ContactDTO contactDTO);
    public void delete(Long id);
    public Contact findContactByOfficialId(OfficialIdTypeEnum officialIDTypeEnum, String officialIDNumber);
    public Contact getOrCreateContact(ContactDTO contactDTO) throws Exception;
}
