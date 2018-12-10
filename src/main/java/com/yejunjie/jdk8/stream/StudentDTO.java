package com.yejunjie.jdk8.stream;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: junjieye
 * @since: 2018/11/23
 * @des
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {


    private String id;
    private String name;
    private Integer mathScore;
    private Integer chineseScore;
    private String area;


}
