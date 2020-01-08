package cn.njyazheng.vo;

import lombok.Data;

import java.util.List;

@Data
public class TreeNode {
    private String title;
    private String id;
    private String filed;
    private List<TreeNode> children;
    private String href;
    private boolean spread = false;
    private boolean checked = false;
    private boolean disabled = false;
    private String parentId;
    private Integer type;
}
