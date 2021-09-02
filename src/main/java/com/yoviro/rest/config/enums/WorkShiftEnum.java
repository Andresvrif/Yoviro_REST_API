package com.yoviro.rest.config.enums;

public enum WorkShiftEnum {
    MORNING(6, 0, 13, 59),
    LATE(14, 0, 21, 59),
    EVENING(22, 0, 5, 59);

    private int startHour;
    private int startMinute;
    private int endHour;
    private int endMinute;

    WorkShiftEnum(int startHour, int startMinute, int endHour, int endMinute) {
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.endHour = endHour;
        this.endMinute = endMinute;
    }
}