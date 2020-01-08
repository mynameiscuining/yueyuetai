package cn.njyazheng.controller.system;

import cn.njyazheng.domain.system.DataDictionary;
import cn.njyazheng.service.system.DataDictionaryService;
import cn.njyazheng.vo.OperatResult;
import cn.njyazheng.vo.PageResult;
import cn.njyazheng.vo.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "dictionary")
public class DictionaryController {
    @Autowired
    private DataDictionaryService dataDictionaryService;

    @GetMapping("/list")
    public PageResult<DataDictionary> getRoleList(DataDictionary dataDictionary, Pagination pagination) {
        return dataDictionaryService.dataDictionaryPageResult(dataDictionary, pagination);
    }

    @PostMapping("/add")
    public OperatResult add(DataDictionary dataDictionary) {
        return dataDictionaryService.add(dataDictionary);
    }

    @PostMapping("/modify")
    public OperatResult modify(DataDictionary dataDictionary) {
        return dataDictionaryService.modify(dataDictionary);
    }

    @PostMapping("/delete")
    public OperatResult delete(DataDictionary dataDictionary) {
        return dataDictionaryService.delete(dataDictionary);
    }
}
