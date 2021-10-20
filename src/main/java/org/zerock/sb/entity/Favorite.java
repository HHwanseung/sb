package org.zerock.sb.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = {"diary","member"})

public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long fno;

    @ManyToOne(fetch = FetchType.LAZY)
    private Diary diary; //다이어리를 가져다 쓰고 싶어서 사용! 에러 뜨는 이뉴 - 관계를 주지 않았기 때문에 엘리먼트 컬렉션을 주던가, 매니투매니를 주던가, 매니투원을 주던가

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    //좋아요 싫어요 좋아요는 +1 싫어요 -1 주면 되기때문에 스코어를 주면 된다.
    private int score;

    @CreationTimestamp//좋아요와 싫어요는 수정이 불가능하다. 싫어요를 누르고 싶다면 좋아요 취소 후 싫어요를 클릭하게 할 수 있도록! 삭제만 가능하게!
    private LocalDateTime regDate;
}
