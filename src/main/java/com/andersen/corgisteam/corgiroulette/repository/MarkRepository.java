package com.andersen.corgisteam.corgiroulette.repository;

import com.andersen.corgisteam.corgiroulette.dto.ResponseGroupedMarksDto;
import com.andersen.corgisteam.corgiroulette.entity.Mark;

import java.util.List;

public interface MarkRepository {

    Mark save(Mark mark);

    List<ResponseGroupedMarksDto> findAllMarksGroupedByUser();

    Mark findById(long id);

    List<Mark> findAllByUserId(long userId);

    void update(Mark mark);

    void delete(long id);

}
