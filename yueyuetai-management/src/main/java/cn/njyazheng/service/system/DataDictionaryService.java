package cn.njyazheng.service.system;

import cn.njyazheng.domain.system.DataDictionary;
import cn.njyazheng.domain.system.DataDictionaryExample;
import cn.njyazheng.domain.system.DataDictionaryKey;
import cn.njyazheng.mapper.system.DataDictionaryMapper;
import cn.njyazheng.vo.OperatResult;
import cn.njyazheng.vo.PageResult;
import cn.njyazheng.vo.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataDictionaryService {
    @Autowired
    private DataDictionaryMapper dataDictionaryMapper;

    public List<DataDictionary> getDataDictionarysBySerialNumber(Integer no) {
        DataDictionaryExample example = new DataDictionaryExample();
        example.createCriteria().andSerialNumberEqualTo(no);
        return dataDictionaryMapper.selectByExample(example);
    }

    public PageResult<DataDictionary> dataDictionaryPageResult(DataDictionary dataDictionary, Pagination pagination) {
        PageResult<DataDictionary> dataDictionaryPageResult = new PageResult<>();
        pagination.setStart((pagination.getPage() - 1) * pagination.getLimit());
        dataDictionaryPageResult.setCode(PageResult.NORMAL_CODE);
        dataDictionaryPageResult.setData(dataDictionaryMapper.selectByName(dataDictionary, pagination));
        dataDictionaryPageResult.setCount(dataDictionaryMapper.selectCountByName(dataDictionary, pagination));
        return dataDictionaryPageResult;
    }

    public DataDictionary getByID(Integer serialNumber, Integer paramno) {
        DataDictionaryKey dataDictionaryKey = new DataDictionaryKey();
        dataDictionaryKey.setSerialNumber(serialNumber);
        dataDictionaryKey.setParamno(paramno);
        return dataDictionaryMapper.selectByPrimaryKey(dataDictionaryKey);
    }

    public OperatResult add(DataDictionary dataDictionary) {
        DataDictionaryKey dataDictionaryKey = new DataDictionaryKey();
        dataDictionaryKey.setSerialNumber(dataDictionary.getSerialNumber());
        dataDictionaryKey.setParamno(dataDictionary.getParamno());
        DataDictionary check = dataDictionaryMapper.selectByPrimaryKey(dataDictionaryKey);
        if (check != null) {
            return new OperatResult(OperatResult.ERROR, "添加编号与" + check.getName() + "的" + check.getParamname() + "编号重复!");
        }
        int insert = dataDictionaryMapper.insert(dataDictionary);
        if (insert > 0) {
            return new OperatResult(OperatResult.SUCCESS, "添加成功!");
        } else {
            return new OperatResult(OperatResult.ERROR, "添加失败!");
        }
    }

    public OperatResult modify(DataDictionary dataDictionary) {
        DataDictionaryKey dataDictionaryKey = new DataDictionaryKey();
        dataDictionaryKey.setSerialNumber(dataDictionary.getSerialNumber());
        dataDictionaryKey.setParamno(dataDictionary.getParamno());
        DataDictionary check = dataDictionaryMapper.selectByPrimaryKey(dataDictionaryKey);
        if (check == null) {
            return new OperatResult(OperatResult.ERROR, "字典项不存在!");
        }
        int modify = dataDictionaryMapper.updateByPrimaryKey(dataDictionary);
        if (modify > 0) {
            return new OperatResult(OperatResult.SUCCESS, "修改成功!");
        } else {
            return new OperatResult(OperatResult.ERROR, "修改失败!");
        }
    }


    public OperatResult delete(DataDictionary dataDictionary) {
        DataDictionaryKey dataDictionaryKey = new DataDictionaryKey();
        dataDictionaryKey.setSerialNumber(dataDictionary.getSerialNumber());
        dataDictionaryKey.setParamno(dataDictionary.getParamno());
        DataDictionary check = dataDictionaryMapper.selectByPrimaryKey(dataDictionaryKey);
        if (check == null) {
            return new OperatResult(OperatResult.ERROR, "字典项不存在!");
        }
        int delete = dataDictionaryMapper.deleteByPrimaryKey(dataDictionary);
        if (delete > 0) {
            return new OperatResult(OperatResult.SUCCESS, "删除成功!");
        } else {
            return new OperatResult(OperatResult.ERROR, "删除失败!");
        }
    }
}
