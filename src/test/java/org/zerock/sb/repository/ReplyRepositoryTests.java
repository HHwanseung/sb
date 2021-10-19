package org.zerock.sb.repository;

import lombok.extern.log4j.Log4j2;
import net.bytebuddy.TypeCache;
import org.apache.catalina.LifecycleState;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.sb.entity.Board;
import org.zerock.sb.entity.Reply;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class ReplyRepositoryTests {

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void insert200() {

        IntStream.rangeClosed(1, 200).forEach(i -> {

            Long bno = (long)(200 - (i % 5)); // 200, 199, 198, 197, 196

            int replyCount = (i % 5); // 0, 1, 2, 3, 4

            IntStream.rangeClosed(0, replyCount).forEach(j -> {

                Board board = Board.builder().bno(bno).build();

                Reply reply = Reply.builder()
                        .replyText("Reply........")
                        .replyer("replyer....")
                        .board(board)
                        .build();

                replyRepository.save(reply);
            });//j loop

        });//i loop
    }

    @Test
    public void testRead() {

        Long rn = 1L;

        Reply reply = replyRepository.findById(rn).get();

        log.info(reply);
    }

    @Test
    public void testByBno() {

        Long bno = 200L;

        List<Reply> replyList
                = replyRepository.findReplyByBoard_BnoOrderByRno(bno);

        replyList.forEach(reply -> log.info(reply));
    }

    @Test
    public void testEx1() {

        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());

        Page<Object[]> result = boardRepository.ex1(pageable);

        log.info(result);

        result.get().forEach(element -> {

            Object[] arr = (Object[])element;

            log.info(Arrays.toString(arr));

        });
    }

    @Test
    public void testListOfBoard() {

        Pageable pageable = PageRequest.of(0, 10, Sort.by("rno").descending());

        Page<Reply> result = replyRepository.getListByBno(197L, pageable);

        log.info(result.getTotalElements());

        result.get().forEach(reply -> log.info(reply));
    }

    @Test
    public void testCountOfBoard() {

        //198번에 120개 댓글 수용
        Long bno = 195L;

        int count = replyRepository.getReplyCountOfBoard(bno);

        int lastPage = (int)(Math.ceil(count / (double)10));

        Pageable pageable = PageRequest.of(lastPage <=0? 0:lastPage -1, 10);

        Page<Reply> result = replyRepository.getListByBno(bno, pageable);

        log.info("total : " + result.getTotalElements());
        log.info("......" + result.getTotalPages());

        result.get().forEach(reply -> {
            log.info(reply);
        });

    }
}
