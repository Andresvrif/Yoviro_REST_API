package com.yoviro.rest.models.repository.projections;

import com.yoviro.rest.config.enums.InventoryRequestStatusEnum;
import com.yoviro.rest.util.StringUtil;

import java.time.LocalDateTime;

public interface SummaryListInventoryRequestNurseProjection {
    String getInventoryRequestNumber();

    String getResidentFirstName();

    String getResidentSecondName();

    String getResidentLastName();

    String getResidentSecondLastName();

    InventoryRequestStatusEnum getStatus();

    String getStoreKeeperFirstName();

    String getStoreKeeperSecondName();

    String getStoreKeeperLastName();

    String getStoreKeeperSecondLastName();

    String getProposalNumber();

    LocalDateTime getCreateAt();

    default String getResidentFullName() {
        String fullName = null;
        if (getResidentFirstName() != null) {
            fullName = fullName == null ? getResidentFirstName().concat(" ") : fullName.concat(getResidentFirstName()).concat(" ");
        }

        if (getResidentSecondName() != null) {
            fullName = fullName == null ? getResidentSecondName().concat(" ") : fullName.concat(getResidentSecondName()).concat(" ");
        }

        if (getResidentLastName() != null) {
            fullName = fullName == null ? getResidentLastName().concat(" ") : fullName.concat(getResidentLastName()).concat(" ");
        }

        if (getResidentSecondLastName() != null) {
            fullName = fullName == null ? getResidentSecondLastName().concat(" ") : fullName.concat(getResidentSecondLastName().concat(" "));
        }

        return fullName == null ? fullName : StringUtil.capitalizeWord(fullName);
    }

    default String getStoreKeeperFullName() {
        String fullName = null;
        if (getStoreKeeperFirstName() != null) {
            fullName = fullName == null ? getStoreKeeperFirstName().concat(" ") : fullName.concat(getStoreKeeperFirstName()).concat(" ");
        }

        if (getStoreKeeperSecondName() != null) {
            fullName = fullName == null ? getStoreKeeperSecondName().concat(" ") : fullName.concat(getStoreKeeperSecondName()).concat(" ");
        }

        if (getStoreKeeperLastName() != null) {
            fullName = fullName == null ? getStoreKeeperLastName().concat(" ") : fullName.concat(getStoreKeeperLastName()).concat(" ");
        }

        if (getStoreKeeperSecondLastName() != null) {
            fullName = fullName == null ? getStoreKeeperSecondLastName().concat(" ") : fullName.concat(getStoreKeeperSecondLastName().concat(" "));
        }

        return fullName == null ? fullName : StringUtil.capitalizeWord(fullName);
    }
}