package cn.njyazheng.vo;

import lombok.Data;

@Data
public class Pagination {
    private Integer page;
    private Integer limit;
    private Integer start;
}
