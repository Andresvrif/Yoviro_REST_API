package com.yoviro.rest.models.entity;

import com.yoviro.rest.util.DateUtil;

import javax.persistence.*;
import javax.swing.tree.DefaultTreeCellEditor;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
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

    @NotNull
    @Temporal(TemporalType.TIME)
    private Date startTime;

    @NotNull
    @Temporal(TemporalType.TIME)
    private Date endTime;

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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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
    public Boolean match(Date referenceDate) {
        Calendar referenceCalendar = DateUtil.dateToCalendar(referenceDate);
        //Date to evaluate
        //LocalDate referenceLocalDate = LocalDate.of(referenceCalendar.get(Calendar.YEAR), referenceCalendar.get(Calendar.MONTH), referenceCalendar.get(Calendar.DAY_OF_MONTH));
        LocalDateTime referenceLocalDate = referenceDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        //If isn't th same day of the week, return false
        if (referenceLocalDate.getDayOfWeek() != this.getDayOfWeek()) return Boolean.FALSE;

        //START DATE TIME RANGE
        Calendar startDateCalendar = DateUtil.dateToCalendar(this.startTime);
        startDateCalendar.set(Calendar.YEAR, referenceCalendar.get(Calendar.YEAR));
        startDateCalendar.set(Calendar.MONTH, referenceCalendar.get(Calendar.MONTH));
        startDateCalendar.set(Calendar.DAY_OF_MONTH, referenceCalendar.get(Calendar.DAY_OF_MONTH));
        Date startDateTime = startDateCalendar.getTime();

        //END DATE TIME RANGE
        Calendar endDateCalendar = DateUtil.dateToCalendar(this.endTime);
        endDateCalendar.set(Calendar.YEAR, referenceCalendar.get(Calendar.YEAR));
        endDateCalendar.set(Calendar.MONTH, referenceCalendar.get(Calendar.MONTH));
        endDateCalendar.set(Calendar.DAY_OF_MONTH, referenceCalendar.get(Calendar.DAY_OF_MONTH));
        Date endDateTime = endDateCalendar.getTime();

        SimpleDateFormat pattern = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        //System.out.println("***************** match - START *****************");
        return startDateTime.compareTo(referenceDate) <=0 && endDateTime.compareTo(referenceDate) >= 0 ;
/*        System.out.println("\tRefDayOfWeek          : " + referenceLocalDate.getDayOfWeek());
        System.out.println("\tWork ShiftDayOfWeek   : " + this.getDayOfWeek());
        System.out.println("\tStart Date Time       : " + pattern.format(startDateTime));
        System.out.println("\tEnd Date Time         : " + pattern.format(endDateTime));
        System.out.println("\tReference Date Time   : " + pattern.format(referenceDate));
        System.out.println("\tResult          : " + result);
        System.out.println("***************** match -  END  *****************");
        return result;*/
    }


    private static final long serialVersionUID = 1L;
}
