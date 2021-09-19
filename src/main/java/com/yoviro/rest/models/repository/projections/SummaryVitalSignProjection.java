package com.yoviro.rest.models.repository.projections;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Value;
import com.yoviro.rest.util.StringUtil;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface SummaryVitalSignProjection {
    Long getId();

    String getArterialPresion();

    BigDecimal getTemperature();

    BigDecimal getOxygenation();

    BigDecimal getGlucose();

    @JsonIgnore
    String getFirstName();

    @JsonIgnore
    String getSecondName();

    @JsonIgnore
    String getLastName();

    @JsonIgnore
    String getSecondLastName();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime getCreateAt();

    default String getFullName() {
        String fullName = getFirstName().concat(" ");
        if (getSecondName() != null) {
            fullName = fullName.concat(getSecondName()).concat(" ");
        }

        if (getLastName() != null) {
            fullName = fullName.concat(getLastName().concat(" "));
        }

        if (getSecondLastName() != null) {
            fullName = fullName.concat(getSecondLastName().concat(" "));
        }

        return StringUtil.capitalizeWord(fullName);
    }
}