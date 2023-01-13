package com.andersen.corgisteam.corgiroulette.service;

import com.andersen.corgisteam.corgiroulette.dto.ResponseGroupedMarksDto;
import com.andersen.corgisteam.corgiroulette.entity.Mark;
import com.andersen.corgisteam.corgiroulette.repository.MarkRepository;
import com.andersen.corgisteam.corgiroulette.service.exception.InvalidNumberException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class MarkServiceImpl implements MarkService {

    private static final Logger log = LoggerFactory.getLogger(MarkServiceImpl.class);

    private final MarkRepository markRepository;

    public MarkServiceImpl(MarkRepository markRepository) {
        this.markRepository = markRepository;
    }

    @Override
    public void create(Mark mark) {
        validate(mark);
        markRepository.save(mark);
        log.info("Successfully created mark with id {}", mark.getId());
    }

    @Override
    public List<ResponseGroupedMarksDto> getAllMarksGroupedByUser() {
        List<ResponseGroupedMarksDto> markDtoList = markRepository.findAllMarksGroupedByUser();
        log.info("Successfully found all marks grouped by users");
        return markDtoList;
    }

    @Override
    public Mark get(long id) {
        Mark mark = markRepository.findById(id);
        log.info("Successfully found mark with id {}", id);
        return mark;
    }

    @Override
    public List<Mark> getAllByUserId(long userId) {
        List<Mark> marks = markRepository.findAllByUserId(userId);
        log.info("Successfully found all marks by user id {}", userId);
        return marks;
    }

    @Override
    public void update(Mark mark) {
        Mark oldMark = markRepository.findById(mark.getId());
        validate(mark);
        markRepository.update(mark);
        log.info("Successfully updated mark with id {}", oldMark.getId());
    }

    @Override
    public void delete(long id) {
        markRepository.delete(id);
        log.info("Successfully deleted mark with id {}", id);
    }

    private void validate(Mark mark) {
        if (mark.getMark() <= 0.0) {
            throw new InvalidNumberException(String.format("Value of mark cannot be lower or equal 0. Presented value: %f.1"
                    , mark.getMark()));
        }
    }
}
