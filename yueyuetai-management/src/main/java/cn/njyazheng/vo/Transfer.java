package cn.njyazheng.vo;

import lombok.Data;

@Data
public class Transfer {
    private Integer value;
    private String title;
    private Boolean disabled = false;
    private Boolean checked = false;
}
