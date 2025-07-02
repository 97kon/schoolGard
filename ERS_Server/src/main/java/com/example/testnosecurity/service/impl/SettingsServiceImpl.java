package com.example.testnosecurity.service.impl;

import com.example.testnosecurity.mapper.SettingsMapper;
import com.example.testnosecurity.pojo.Settings;
import com.example.testnosecurity.service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SettingsServiceImpl implements SettingsService {

    @Autowired
    private SettingsMapper settingsMapper;

    @Override
    public Settings getSettings() {
        return settingsMapper.selectSettings();
    }

    @Override
    public boolean updateSettings(Settings settings) {
        int affectedRows = settingsMapper.updateSettings(settings);
        return affectedRows > 0;
    }

    @Override
    public int getLateMinutes() {
        Settings settings = settingsMapper.selectSettings();
        return settings != null ? settings.getLate() : 30; // 默认30分钟
    }

    @Override
    public int getNoshowTimes() {
        Settings settings = settingsMapper.selectSettings();
        return settings != null ? settings.getNoshowtimes() : 3; // 默认3次
    }

    @Override
    public int getLimitDays() {
        Settings settings = settingsMapper.selectSettings();
        return settings != null ? settings.getLimitdays() : 30; // 默认30天
    }
}
