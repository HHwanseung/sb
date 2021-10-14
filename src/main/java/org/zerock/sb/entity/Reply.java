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
@ToString(exclude = "board")
public class Reply { //엔티티 인데 아이디가 없어서 이렇게만 하면 오류

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    private String replyText;

    private String replyer;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board; //옛날엔 bno만 걸었지만 객체와 객체기 때문에 근데 오류 엔티티와 엔티티간의 관계를 서술해주지 않으면 오류


    @CreationTimestamp
    private LocalDateTime replyDate;
}
