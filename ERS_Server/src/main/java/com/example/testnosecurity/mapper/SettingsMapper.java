package com.example.testnosecurity.mapper;

import com.example.testnosecurity.pojo.Settings;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SettingsMapper {
    Settings selectSettings();
    int updateSettings(Settings settings);
}
