package com.yoviro.rest.models.entity;

import com.yoviro.rest.util.DateUtil;
import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Entity
public class WorkShift {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,
            nullable = false)
    private String name;

    @OneToMany(mappedBy = "workShift", fetch = FetchType.LAZY)
    private List<WorkshiftItem> workshiftItems;

    @OneToMany(mappedBy = "workShift", fetch = FetchType.LAZY)
    private List<Worker> workers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<WorkshiftItem> getWorkshiftItems() {
        return workshiftItems;
    }

    public void setWorkshiftItems(List<WorkshiftItem> workshiftItems) {
        this.workshiftItems = workshiftItems;
    }

    public List<Worker> getWorkers() {
        return workers;
    }

    public void setWorkers(List<Worker> workers) {
        this.workers = workers;
    }

    private static final long serialVersionUID = 1L;

    /**
     * Author : Andrés V.
     *
     * @return
     */
    public Boolean isIn(LocalDateTime referenceDate,
                        ActivityPattern activityPattern) {

        //Mix Reference date and activity pattern hour
        LocalTime activityPatternHour = activityPattern.getHourFrequency();
        LocalDateTime reference = LocalDateTime.of(referenceDate.toLocalDate(), referenceDate.toLocalTime());
        reference.withHour(activityPatternHour.getHour())
                .withMinute(activityPatternHour.getMinute())
                .withSecond(activityPatternHour.getSecond());

        List<WorkshiftItem> workshiftItems = this.workshiftItems;
        if (workshiftItems == null || workshiftItems.isEmpty()) return Boolean.FALSE;

        return workshiftItems.stream().anyMatch(workshiftItem -> workshiftItem.match(reference));
    }
}