package org.zerock.sb.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.zerock.sb.entity.Board;
import org.zerock.sb.entity.Reply;

import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class ReplyRepositoryTests {

    @Autowired
    private ReplyRepository replyRepository;

    @Test
    public void insert200(){

        IntStream.rangeClosed(1,200).forEach(i -> {

            Long bno = (long)(200 - (i % 5)); //200,199,198,196,196

            int replyCount = (i % 5); //0,1,2,3,4, 이거 만큼 추가

            IntStream.rangeClosed(0,replyCount).forEach(j -> {

                Board board = Board.builder().bno(bno).build(); //bno x board인데 Id가 중요하니깐

                Reply reply = Reply.builder()
                        .replyText("Reply....")
                        .replyer("Replyer....")
                        .board(board)
                        .build();

                replyRepository.save(reply);


            });

        });

    }

    @Test
    public void testRead(){

        Long rn = 1L;

        Reply reply = replyRepository.findById(rn).get();

        log.info(reply);



    }

    @Test
    public void testByBno(){
        Long bno = 200L;

        List<Reply> replyList = replyRepository.findReplyByBoard_BnoOrderByRno(bno);

        replyList.forEach(reply -> log.info(reply));
    }

    @Test
    public void testListOfBoard() {

        Pageable pageable =
                PageRequest.of(0,10, Sort.by("rno").descending());

        Page<Reply> result = replyRepository.getListByBno(197L,pageable);

        log.info(result.getTotalElements());

        result.get().forEach(reply -> log.info(reply));


    }

    @Test
    public void testCountOfBoard() {

        long bno = 198L;

        //120
        int count = replyRepository.getReplyCountOfBoard(bno);
        int lastPage = (int)(Math.ceil(count/10.0));

        if (lastPage == 0){ //댓글이 0개
            lastPage = 1;
        }

        //0부터 시작하는 페이지번호 , 사이즈 , 소트
        Pageable pageable = PageRequest.of(lastPage -1, 10);

        Page<Reply> result = replyRepository.getListByBno(bno, pageable);

        log.info("total:" + result.getTotalElements());
        log.info("..." + result.getTotalElements());

        result.get().forEach(reply -> {
            log.info(reply);
        });

    }

}
