package com.example.testnosecurity.service;

import com.example.testnosecurity.pojo.Opentime;
import java.util.List;
import java.util.Map;

public interface OpentimeService {
    List<Opentime> getAllOpentimes();
    Map<String, Object> addOpentime(Opentime opentime);
}
