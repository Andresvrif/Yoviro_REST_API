package com.yoviro.rest.service.interfaces;

import com.yoviro.rest.dto.*;
import com.yoviro.rest.dto.search.SearchAgreementDTO;
import com.yoviro.rest.models.entity.Agreement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IAgreementService {
    AgreementDTO createNewFullAgreement(ContractorDTO contractorDTO,
                                        ResidentDTO residentDTO,
                                        SubmissionDTO submissionDTO) throws Exception;

    AgreementDTO cancelAgreement(AgreementDTO agreementDTO,
                                 CancellationDTO cancellationDTO) throws Exception;

    Page<Agreement> searchAgreementsByContact(Pageable pageable, SearchAgreementDTO searchAgreementDTO);

    Agreement findAgreementByAgreementNumber(String agreementNumber);
}
