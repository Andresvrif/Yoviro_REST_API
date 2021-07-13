package com.yoviro.rest.service;

import com.yoviro.rest.dto.ContactDTO;
import com.yoviro.rest.dto.OfficialIdDTO;
import com.yoviro.rest.models.entity.Contact;
import com.yoviro.rest.models.repository.ContactRepository;
import com.yoviro.rest.service.interfaces.IContactService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ContactServiceImpl implements IContactService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ContactRepository contactRepository;

    @Override
    public List<ContactDTO> findAll() {
        return null;
    }

    @Override
    public ContactDTO save(ContactDTO contactDto) {
        Contact contact = modelMapper.map(contactDto, Contact.class);
        contact = contactRepository.save(contact);
        return modelMapper.map(contact, ContactDTO.class);
    }

    @Override
    public void delete(Long id) {
        //contactoDao.deleteById(id);
    }

    @Override
    public ContactDTO findContactByOfficialId(OfficialIdDTO officialIdDTO) {
        Contact contact = contactRepository.findByOfficialID(officialIdDTO.getOfficialIdType(), officialIdDTO.getOfficialIdNumber());
        return modelMapper.map(contact, ContactDTO.class);
    }
}