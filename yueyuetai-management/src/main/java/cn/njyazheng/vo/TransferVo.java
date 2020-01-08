package cn.njyazheng.vo;

import lombok.Data;

import java.util.List;

@Data
public class TransferVo {
    private List<Transfer> data;
    private List<Integer>select;
}
