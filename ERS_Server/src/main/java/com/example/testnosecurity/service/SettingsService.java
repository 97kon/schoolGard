package com.example.testnosecurity.service;

import com.example.testnosecurity.pojo.Settings;

public interface SettingsService {
    int getLateMinutes();
    int getNoshowTimes();
    int getLimitDays();
    Settings getSettings();
    boolean updateSettings(Settings settings);
}
