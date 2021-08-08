package com.yoviro.rest.service.interfaces;

import com.yoviro.rest.dto.*;

public interface IAgreementService {
    AgreementDTO createNewFullAgreement(ContractorDTO contractorDTO,
                                        ResidentDTO residentDTO,
                                        SubmissionDTO submissionDTO);

    AgreementDTO cancelAgreement(AgreementDTO agreementDTO,
                                 CancellationDTO cancellationDTO);
}
