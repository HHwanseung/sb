package org.zerock.sb.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.zerock.sb.entity.Reply;
import org.zerock.sb.dto.PageRequestDTO;
import org.zerock.sb.dto.PageResponseDTO;
import org.zerock.sb.dto.ReplyDTO;
import org.zerock.sb.repository.ReplyRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService{

    private final ModelMapper modelMapper;
    private final ReplyRepository replyRepository;


    @Override
    public PageResponseDTO<ReplyDTO> getListOfBoard(Long bno, PageRequestDTO pageRequestDTO) {

        Pageable pageable = null;

        if(pageRequestDTO.getPage() == -1){
            int lastPage = calcLastPage(bno, pageRequestDTO.getSize()); // 두가지 결과 경우 -1 : 댓글이 없는경우, 숫자 : 마지막 댓글 페이지 넘버
            if(lastPage <= 0){
                lastPage = 1;
            }
            pageRequestDTO.setPage(lastPage);
        }
            pageable = PageRequest.of(pageRequestDTO.getPage() -1, pageRequestDTO.getSize());

        Page<Reply> result = replyRepository.getListByBno(bno, pageable);

        List<ReplyDTO> dtoList = result.get().map(reply -> modelMapper.map(reply, ReplyDTO.class)).collect(Collectors.toList());

        dtoList.forEach(replyDTO -> log.info(replyDTO));

        return new PageResponseDTO<>(pageRequestDTO, (int)result.getTotalElements(), dtoList);
    }

    @Override
    public Long register(ReplyDTO replyDTO) {

        //Board의 자동병환이 이루어 지는지 확인 필요
//        Board board = Board.builder().bno(replyDTO.getBno()).build();

        Reply reply = modelMapper.map(replyDTO, Reply.class);

        log.info(reply);

        replyRepository.save(reply);

        return reply.getRno();
    }

    private int calcLastPage(Long bno, double size) {
        int count = replyRepository.getReplyCountOfBoard(bno);
        int lastPage = (int)(Math.ceil(count / size));

        return lastPage;
    }
}
