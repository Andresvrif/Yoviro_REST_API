package com.yoviro.rest.config.mapper;

import com.yoviro.rest.dto.ContactDTO;
import com.yoviro.rest.dto.OfficialIdDTO;
import com.yoviro.rest.dto.PersonDTO;
import com.yoviro.rest.models.entity.Company;
import com.yoviro.rest.models.entity.Contact;
import com.yoviro.rest.models.entity.OfficialId;
import com.yoviro.rest.models.entity.Person;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import java.util.ArrayList;
import java.util.List;

public class ContactToDTOConverter implements Converter<Contact, ContactDTO> {

    @Override
    public ContactDTO convert(MappingContext<Contact, ContactDTO> mappingContext) {
        Contact src = mappingContext.getSource();
        ContactDTO dest = mappingContext.getDestination();
        if (src == null && dest == null) return null;

        if (src != null && dest == null) {
            if (src instanceof Person) {
                Person person = (Person) src;
                PersonDTO personDTO = new PersonDTO();
                personDTO.setId(person.getId());
                personDTO.setFirstName(person.getName());
                personDTO.setSecondName(person.getSecondName());
                personDTO.setLastName(person.getLastName());
                personDTO.setSecondLastName(person.getSecondLastName());
                personDTO.setBirthDate(person.getBirthDate());
                personDTO.setPhoto(person.getPhoto());
                List<OfficialIdDTO> officialIdDTOsToBeAdded = new ArrayList<>();
                for (OfficialId officialIdToBeAdded : person.getOfficialIds()) {
                    OfficialIdDTO officialIdDTO = OfficialIdDTO.instanceFromEntity(officialIdToBeAdded);
                    officialIdDTO.setContact(personDTO);

                    officialIdDTOsToBeAdded.add(officialIdDTO);
                }
                personDTO.setOfficialIds(officialIdDTOsToBeAdded);

                return personDTO;
            } else if (src instanceof Company) {

            }
        }
        return null;
    }
}
