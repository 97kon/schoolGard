package com.example.testnosecurity.mapper;

import com.example.testnosecurity.pojo.Opentime;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface OpentimeMapper {
    List<Opentime> selectAll();
    int insert(Opentime opentime);
}
