package com.espy.mps.interfaces;

public interface AttendanceCallbacks {
    void onAttendanceSend(String result);

    void onSendingAttendanceFailed(String message);
}
