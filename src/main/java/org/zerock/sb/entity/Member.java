package org.zerock.sb.entity;


import lombok.*;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString //JPA는 Naming 해주지 않는다. from 이라던가 user처럼 겹치는 단어는 사용하지 않는게 좋다.
public class Member {

    //최근 트렌드는 이메일을 이용하여 아이디로 사용하는 경우가 있다.
    @Id
    private String mid;

    private String mpw;

    private String mname;

    @Builder.Default
    @ElementCollection(fetch = FetchType.LAZY)
    private Set<MemberRole> roleSet = new HashSet<>();

    public void changePassword(String password){
        this.mpw = password;
    }
}
