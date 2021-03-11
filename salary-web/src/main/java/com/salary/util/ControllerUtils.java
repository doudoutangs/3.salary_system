package com.salary.util;

import com.salary.contants.ExceptionEnum;
import com.salary.core.R;
import com.salary.config.DateEditor;
import com.salary.exception.BizException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.mvc.method.annotation.ExtendedServletRequestDataBinder;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ControllerUtils extends R {
    private static Validator validator;

    public static <T> T bindParams(HttpServletRequest request, Class<T> formClz) throws Exception {
        T bindObject = BeanUtils.instantiateClass(formClz);
        ExtendedServletRequestDataBinder dataBinder = new ExtendedServletRequestDataBinder(bindObject, "bindObject");
        dataBinder.setAutoGrowCollectionLimit(1024);
        dataBinder.registerCustomEditor(Date.class, new DateEditor());
        dataBinder.setValidator(getValidator());
        dataBinder.bind(request);
        dataBinder.validate();
        checkErrors(dataBinder.getBindingResult());
        handlerEmpty(bindObject);
        return bindObject;
    }

    private static Validator getValidator() {
        if (validator == null) {
            LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
            localValidatorFactoryBean.afterPropertiesSet();
            validator = localValidatorFactoryBean;
        }
        return validator;
    }

    private static void checkErrors(BindingResult br) throws BizException {
        List<FieldError> errors = br.getFieldErrors();
        if (errors != null && errors.size() > 0) {
            Iterator var2 = errors.iterator();
            if (var2.hasNext()) {
                BizException e = new BizException(ExceptionEnum.SYSTEM_ERRO);
                throw e;
            }
        }

    }

    private static void handlerEmpty(Object object) throws Exception {
        Class clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (!field.getName().equals("serialVersionUID")) {
                field.setAccessible(true);
                Object val = field.get(object);
                if (StringUtils.isBlank(String.valueOf(val))) {
                    field.set(object, null);
                }
            }
        }

    }


}
