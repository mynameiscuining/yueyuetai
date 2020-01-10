package cn.njyazheng.vo;

public class OperatResult {
    public final static Integer SUCCESS = 0;
    public final static Integer ERROR = 9999;
    private Integer status;
    private String msg;
    private Object object;

    public OperatResult() {
    }

    public OperatResult(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }


    public OperatResult(Integer status, String msg, Object object) {
        this.status = status;
        this.msg = msg;
        this.object = object;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public static OperatResult modifySuccess() {
        return new OperatResult(SUCCESS, "修改成功!");
    }

    public static OperatResult addSuccess() {
        return new OperatResult(SUCCESS, "添加成功!");
    }

    public static OperatResult deleteSuccess() {
        return new OperatResult(SUCCESS, "删除成功!");
    }

    public static OperatResult deleteFailure() {
        return new OperatResult(ERROR, "删除失败!");
    }

    public static OperatResult addFailure() {
        return new OperatResult(ERROR, "添加失败!");
    }

    public static OperatResult modifyFailure() {
        return new OperatResult(ERROR, "修改失败!");
    }
}
