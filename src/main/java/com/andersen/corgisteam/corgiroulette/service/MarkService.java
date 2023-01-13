package com.andersen.corgisteam.corgiroulette.service;

import com.andersen.corgisteam.corgiroulette.dto.ResponseGroupedMarksDto;
import com.andersen.corgisteam.corgiroulette.entity.Mark;

import java.util.List;

public interface MarkService {
    void create(Mark mark);

    List<ResponseGroupedMarksDto> getAllMarksGroupedByUser();

    Mark get(long id);

    List<Mark> getAllByUserId(long userId);

    void update(Mark mark);

    void delete(long id);
}
