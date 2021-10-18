package org.zerock.sb.service;

import org.zerock.sb.dto.DiaryDTO;

import javax.transaction.Transactional;

@Transactional
public interface DiaryService {

    Long register(DiaryDTO dto);

    DiaryDTO read(Long dno);
}
