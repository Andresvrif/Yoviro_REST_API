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
        String fullName = null;
        if (getFirstName() != null) {
            fullName = fullName == null ? getFirstName().concat(" ") : fullName.concat(getFirstName()).concat(" ");
        }

        if (getSecondName() != null) {
            fullName = fullName == null ? getSecondName().concat(" ") : fullName.concat(getSecondName()).concat(" ");
        }

        if (getLastName() != null) {
            fullName = fullName == null ? getLastName().concat(" ") : fullName.concat(getLastName()).concat(" ");
        }

        if (getSecondLastName() != null) {
            fullName = fullName == null ? getSecondLastName().concat(" ") : fullName.concat(getSecondLastName().concat(" "));
        }

        return fullName == null ? fullName : StringUtil.capitalizeWord(fullName);
    }
}