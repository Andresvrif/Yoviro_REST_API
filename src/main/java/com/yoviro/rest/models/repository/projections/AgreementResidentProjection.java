package com.yoviro.rest.models.repository.projections;

import org.springframework.beans.factory.annotation.Value;

public interface AgreementResidentProjection {
    String getFirstName();

    String getSecondName();

    String getFirstLastName();

    String getSecondLastName();

    String getAgreementNumber();

    @Value("#{target.firstName + ' ' + target.secondName + ' ' + target.firstLastName + ' ' + target.secondLastName}")
    String getFullName();
}
