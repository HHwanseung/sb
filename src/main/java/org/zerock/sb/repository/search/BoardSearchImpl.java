package org.zerock.sb.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.sb.entity.Board;
import org.zerock.sb.entity.QBoard;
import org.zerock.sb.entity.QReply;

import javax.persistence.Query;
import java.util.List;
import java.util.stream.Collectors;


@Log4j2
public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch {

    public BoardSearchImpl() {
        super(Board.class);
    }

    @Override
    public Page<Board> search1(char[] typeArr, String keyword, Pageable pageable) {
        log.info("-------------------------search1");

        //3번째 방법
//        log.info(this.getQuerydsl().createQuery(QBoard.board).fetch());

        //2번째 방법
//        Query query = this.getEntityManager().createQuery("select b from Board b order by b.bno desc ");
//
//        log.info(query.getResultList());


        //1번째 방법을 가장 많이 사용
        QBoard board = QBoard.board;

        JPQLQuery<Board> jpqlQuery = from(board);

//
//        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());
//
//
//        String keyword = "10";
//        char[] typeArr = {'T', 'C', 'W'};
        //검색조건이 있다면
        if (typeArr != null && typeArr.length > 0) {

            BooleanBuilder condition = new BooleanBuilder(); //BooleanBuilder는 T/F 만 사용 가능

            for (char type : typeArr) {

                if (type == 'T') {
                    condition.or(board.title.contains(keyword)); //원래 코드에 계속 살을 붙이는 형태
                } else if (type == 'C') {
                    condition.or(board.content.contains(keyword));
                } else if (type == 'W') {
                    condition.or(board.writer.contains(keyword));
                }
            }
            jpqlQuery.where(condition);
        }


        jpqlQuery.where(board.bno.gt(0L)); //bno>0 조건 줄때 사용

        JPQLQuery<Board> pagingQuery =
                this.getQuerydsl().applyPagination(pageable, jpqlQuery);

        List<Board> boardList = pagingQuery.fetch();
        long totalCount = pagingQuery.fetchCount(); //카운트 쿼리를 날려준다

        return new PageImpl<>(boardList, pageable, totalCount); //동적 검색이 끝난다.


    }

    @Override
    public Page<Object[]> searchWithReplyCount(char[] typeArr, String keyword,Pageable pageable) {
        log.info("searchWithReplyCount");

        //1. EntityManager를 이용해서 Query
        //2. getQuerydsl( ) 을 이용하는 방식

        //Query를 만들때는 Q도메인 -- 값을 뽑을때는 엔티티 타입
        QBoard qBoard = QBoard.board;
        QReply qReply = QReply.reply;

        JPQLQuery<Board> query = from(qBoard);
        query.leftJoin(qReply).on(qReply.board.eq(qBoard));
        query.groupBy(qBoard);

        //검색조건이 있다면
        if (typeArr != null && typeArr.length > 0) {

            BooleanBuilder condition = new BooleanBuilder(); //BooleanBuilder는 T/F 만 사용 가능

            for (char type : typeArr) {
                if (type == 'T') {
                    condition.or(qBoard.title.contains(keyword)); //원래 코드에 계속 살을 붙이는 형태
                } else if (type == 'C') {
                    condition.or(qBoard.content.contains(keyword));
                } else if (type == 'W') {
                    condition.or(qBoard.writer.contains(keyword));
                }
            }
            query.where(condition);
        }

        JPQLQuery<Tuple> selectQuery = query.select(qBoard.bno, qBoard.title, qBoard.writer,
                qBoard.regDate, qReply.count());

        this.getQuerydsl().applyPagination(pageable, selectQuery);

        List<Tuple> tupleList = selectQuery.fetch();
        long totalCount = selectQuery.fetchCount();


        List<Object[]> arr = tupleList.stream().map(tuple -> tuple.toArray()).collect(Collectors.toList());

        return new PageImpl<>(arr,pageable,totalCount);
    }
}

