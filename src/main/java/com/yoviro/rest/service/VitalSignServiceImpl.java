package com.yoviro.rest.service;

import com.yoviro.rest.dto.ActivityDTO;
import com.yoviro.rest.dto.OfficialIdDTO;
import com.yoviro.rest.dto.VitalSignDTO;
import com.yoviro.rest.models.entity.Resident;
import com.yoviro.rest.models.entity.VitalSign;
import com.yoviro.rest.models.repository.ResidentRepository;
import com.yoviro.rest.models.repository.VitalSignRepository;
import com.yoviro.rest.models.repository.projections.SummaryVitalSignProjection;
import com.yoviro.rest.service.interfaces.IVitalSignService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VitalSignServiceImpl implements IVitalSignService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    ResidentRepository residentRepository;

    @Autowired
    VitalSignRepository vitalSignRepository;

    @Override
    public Page<SummaryVitalSignProjection> findAll(Pageable pageable) {
        return vitalSignRepository.summaryListAll(pageable);
    }

    @Override
    public Page<SummaryVitalSignProjection> summaryList(Pageable pageable, String firstName) {
        firstName = "%".concat(firstName).concat("%");
        return vitalSignRepository.summaryListLikeFirstName(pageable, firstName);
    }

    @Override
    public VitalSign createOrUpdate(OfficialIdDTO officialIdDTO,
                                    VitalSignDTO vitalSignDTO,
                                    ActivityDTO activityDTO) throws Exception {
        //Save Data
        VitalSign vitalSign;
        Optional<VitalSign> optionalVitalSign = null;
        if (vitalSignDTO.getId() != null) {
            optionalVitalSign = vitalSignRepository.findById(vitalSignDTO.getId());
            vitalSign = optionalVitalSign.get();
            if (vitalSign == null) {
                throw new Exception("Not found vital sign");
            }

            //Update fields, don't update field
            vitalSign.setGlucose(vitalSignDTO.getGlucose());
            vitalSign.setOxygenation(vitalSignDTO.getOxygenation());
            vitalSign.setTemperature(vitalSignDTO.getTemperature());
            vitalSign.setArterialPresion(vitalSignDTO.getArterialPresion());
            vitalSign.setObservation(vitalSignDTO.getObservation());
        } else {
            Resident resident = residentRepository.findByOfficialID(officialIdDTO.getOfficialIdType(), officialIdDTO.getOfficialIdNumber());
            if (resident == null) throw new Exception("Resident not found");

            //New vital sign
            vitalSign = modelMapper.map(vitalSignDTO, VitalSign.class);
            vitalSign.setResident(resident);
        }

        if (activityDTO != null) {
            //TODO actualizar actividad a COMPLETED
            //TODO API Rest q retorne el RESIDENTE en base al id de la actividad
        }

        return vitalSignRepository.save(vitalSign);
    }
}
