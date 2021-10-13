package com.yoviro.rest.models.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("DIRECTOR")
public class Director extends Worker {
    @OneToMany(mappedBy = "evaluator",
            fetch = FetchType.LAZY)
    private List<Proposal> proposals = new ArrayList<>();

    public List<Proposal> getProposals() {
        return proposals;
    }

    public void setProposals(List<Proposal> proposals) {
        this.proposals = proposals;
    }
}
