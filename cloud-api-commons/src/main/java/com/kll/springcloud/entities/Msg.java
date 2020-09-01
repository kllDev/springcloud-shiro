package com.kll.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Msg implements Serializable {
    private Integer id;
    private String nickname;
    private String createTime;
    private String message;
}
