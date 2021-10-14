package org.zerock.sb.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerock.sb.entity.Board;
import org.zerock.sb.repository.search.BoardSearch;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board,Long>, BoardSearch {

    @Query("select b.bno,b.title,b.writer,b.regDate, count(r) from Board b left join Reply r on r.board = b group by b")
    Page<Object[]> ex1(Pageable pageable);

}
