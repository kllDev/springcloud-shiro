package com.kll.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Permission {
    private Integer id;
    private Integer parent_id;
    private String name;
    private String url;
    private Integer menu_type;
    private String perms;
    private Integer sort_no;
    private Integer del_flag;

}
