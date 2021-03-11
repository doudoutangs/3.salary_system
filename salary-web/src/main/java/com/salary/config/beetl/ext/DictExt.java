package com.salary.config.beetl.ext;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.salary.modules.sys.dict.entity.SysDict;
import com.salary.modules.sys.dict.service.ISysDictService;
import com.salary.util.SpringContextUtil;

import java.util.ArrayList;
import java.util.List;

public class DictExt {


    /**
     * 是否存在字典
     *
     * @param dictType
     * @return
     */
    public boolean hasDict(String dictType) {
        SysDict sysDict = get(dictType);
        if (sysDict == null) {
            return false;
        }
        return true;
    }

    /**
     * 查询字典以及字典下的子集
     *
     * @param dictType
     * @return
     */
    public List<SysDict> queryDictsByParent(String dictType) {
        ISysDictService sysDictService = (ISysDictService) SpringContextUtil.getBean("sysDictService");
        List<SysDict> sysDicts = new ArrayList<>();
        SysDict sysDict = get(dictType);
        QueryWrapper children = new QueryWrapper();
        children.eq("pid",sysDict.getId());
        sysDicts.addAll(sysDictService.list(children));
        return sysDicts;
    }


    /**
     * 查询父级字典
     * @param dictType
     * @return
     */
    private SysDict get(String dictType) {
        ISysDictService sysDictService = (ISysDictService) SpringContextUtil.getBean("sysDictService");
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("code", dictType);
        SysDict sysDict = sysDictService.getOne(queryWrapper);
        return sysDict;
    }
}
