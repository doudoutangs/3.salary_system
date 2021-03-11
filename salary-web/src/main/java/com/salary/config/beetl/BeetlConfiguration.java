package com.salary.config.beetl;

import com.salary.config.beetl.ext.DictExt;
import com.salary.config.beetl.ext.ShiroExt;
import org.beetl.ext.spring.BeetlGroupUtilConfiguration;

public class BeetlConfiguration extends BeetlGroupUtilConfiguration {

    @Override
    protected void initOther() {
        groupTemplate.registerFunctionPackage("shiro", new ShiroExt());
        groupTemplate.registerFunctionPackage("dict", new DictExt());
    }
}
