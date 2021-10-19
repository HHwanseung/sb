package org.zerock.sb.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.sb.dto.PageRequestDTO;
import org.zerock.sb.dto.ReplyDTO;

@SpringBootTest
@Log4j2
public class ReplyServiceTests {

    @Autowired
    private ReplyService replyService;

    @Test
    public void testRegister() {

        ReplyDTO replyDTO = ReplyDTO.builder()
                .bno(198L)
                .replyText("198댓글")
                .replyer("edum")
                .build();

        replyService.register(replyDTO);
    }

    @Test
    public void testList() {

        Long bno = 195L;
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(-1)
                .build();

        log.info(replyService.getListOfBoard(bno, pageRequestDTO));
    }
}
