package org.zerock.sb.entity;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class DiaryPicture implements Comparable<DiaryPicture>{

    private String uuid;
    private String fileName;
    private String savePath;
    private int idx;


    @Override
    public int compareTo(DiaryPicture o) {
        return this.idx - o.idx  ;
    }
}
