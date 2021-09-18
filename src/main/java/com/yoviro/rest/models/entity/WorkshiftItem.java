package com.yoviro.rest.models.entity;

import com.yoviro.rest.util.DateUtil;

import javax.persistence.*;
import javax.swing.tree.DefaultTreeCellEditor;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Calendar;
import java.util.Date;

@Entity
public class WorkshiftItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_shift_id")
    private WorkShift workShift;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public WorkShift getWorkShift() {
        return workShift;
    }

    public void setWorkShift(WorkShift workShift) {
        this.workShift = workShift;
    }

    public WorkShift getWorkshift() {
        return workShift;
    }

    public void setWorkshift(WorkShift workshift) {
        this.workShift = workshift;
    }

    /***
     * Author: Andrés V.
     * Desc : Checks if date time match with the work shift item
     * @param referenceDate
     * @return
     */
    public Boolean match(LocalDateTime referenceDate) {
        //If isn't th same day of the week, return false
        if (referenceDate.getDayOfWeek() != this.getDayOfWeek()) return Boolean.FALSE;

        //START DATE TIME RANGE
        LocalDateTime startDateTime = LocalDateTime.of(referenceDate.toLocalDate(), this.startTime);

        /*
        LocalDateTime startDateTime = LocalDateTime.of(this.startTime.toLocalDate(), this.startTime.toLocalTime());
        startDateTime = startDateTime.withYear(referenceDate.getYear())
                                     .withMonth(referenceDate.getMonthValue())
                                     .withDayOfMonth(referenceDate.getDayOfMonth());
         */

        //END DATE TIME RANGE
        LocalDateTime endDateTime = LocalDateTime.of(referenceDate.toLocalDate(), this.endTime);
        /*
        endDateTime = endDateTime.withYear(referenceDate.getYear())
                                     .withMonth(referenceDate.getMonthValue())
                                     .withDayOfMonth(referenceDate.getDayOfMonth());
        */

        return startDateTime.compareTo(referenceDate) <= 0 && endDateTime.compareTo(referenceDate) >= 0;
    }

    private static final long serialVersionUID = 1L;
}
