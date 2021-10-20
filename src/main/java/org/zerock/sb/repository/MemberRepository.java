package org.zerock.sb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.sb.entity.Member;

//entity 작업 끝난후 레파지토리 생성해주기!!
//crud 하기 위해 필요하다
public interface MemberRepository extends JpaRepository <Member, String> {


}
