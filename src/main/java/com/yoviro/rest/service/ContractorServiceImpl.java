package com.yoviro.rest.service;

import com.yoviro.rest.dto.ContactDTO;
import com.yoviro.rest.dto.ContractorDTO;
import com.yoviro.rest.dto.OfficialIdDTO;
import com.yoviro.rest.models.entity.Contact;
import com.yoviro.rest.models.entity.Person;
import com.yoviro.rest.models.repository.ContractorRepository;
import com.yoviro.rest.models.entity.Contractor;
import com.yoviro.rest.service.interfaces.IContactService;
import com.yoviro.rest.service.interfaces.IContractorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContractorServiceImpl implements IContractorService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ContractorRepository contractorRepository;

    @Autowired
    private IContactService contactService;

    @Override
    public Contractor save(ContractorDTO contractorDTO) {
        Contractor contractor = modelMapper.map(contractorDTO, Contractor.class);
        return contractorRepository.save(contractor);
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Contractor getOrCreateContractor(ContractorDTO contractorDTO) throws Exception {
        ContactDTO contactDTO = contractorDTO.getPerson();
        OfficialIdDTO officialIdDTO = contactDTO.getOfficialIds().get(0);
        Contractor contractor = contractorRepository.findByOfficialID(officialIdDTO.getOfficialIdType(), officialIdDTO.getOfficialIdNumber());
        if (contractor == null) {
            Contact contact = contactService.getOrCreateContact(contactDTO);
            contractor = new Contractor();
            contractor.setPerson((Person) contact);

            return contractorRepository.save(contractor);
        } else {
            return contractor;
        }
    }
}