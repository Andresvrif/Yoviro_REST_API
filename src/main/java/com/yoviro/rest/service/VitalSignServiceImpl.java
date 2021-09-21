package com.yoviro.rest.service;

import com.yoviro.rest.config.enums.ActivityStatusEnum;
import com.yoviro.rest.dto.ActivityDTO;
import com.yoviro.rest.dto.OfficialIdDTO;
import com.yoviro.rest.dto.VitalSignDTO;
import com.yoviro.rest.models.entity.Activity;
import com.yoviro.rest.models.entity.Resident;
import com.yoviro.rest.models.entity.VitalSign;
import com.yoviro.rest.models.repository.ResidentRepository;
import com.yoviro.rest.models.repository.VitalSignRepository;
import com.yoviro.rest.models.repository.projections.SummaryVitalSignProjection;
import com.yoviro.rest.service.interfaces.IActivityService;
import com.yoviro.rest.service.interfaces.IVitalSignService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VitalSignServiceImpl implements IVitalSignService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    ResidentRepository residentRepository;

    @Autowired
    VitalSignRepository vitalSignRepository;

    @Autowired
    IActivityService activityService;

    @Override
    public Page<SummaryVitalSignProjection> findAll(Pageable pageable) {
        return vitalSignRepository.summaryListAll(pageable);
    }

    @Override
    public Page<SummaryVitalSignProjection> summaryList(Pageable pageable, String firstName) {
        firstName = "%".concat(firstName).concat("%");
        return vitalSignRepository.summaryListLikeFirstName(pageable, firstName);
    }

    /*
     * Author : Andrés V.
     * Desc : Creates vital sign and if id activity has been sent, it update his status to COMPLETED
     */
    @Transactional
    @Override
    public VitalSign createOrUpdate(OfficialIdDTO officialIdDTO,
                                    VitalSignDTO vitalSignDTO,
                                    ActivityDTO activityDTO) throws Exception {
        //Save Data
        VitalSign vitalSign;
        Optional<VitalSign> optionalVitalSign = null;
        if (vitalSignDTO.getId() != null) {
            //We're talking about an update process
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
            //Stablish new Status
            activityDTO.setStatus(ActivityStatusEnum.COMPLETED);

            //Make entity to be updated
            Activity activityToBeUpdated = modelMapper.map(activityDTO, Activity.class);
            List<Activity> activities = new ArrayList<>();
            activities.add(activityToBeUpdated);

            //Update activity
            activityService.updateStatusActivities(activities);
        }

        return vitalSignRepository.save(vitalSign);
    }
}
