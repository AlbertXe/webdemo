package com.aop;

import com.util.DateUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DateFormatValidator implements ConstraintValidator<DateFormat,String> {
    private DateFormat dateFormat;
    @Override
    public void initialize(DateFormat constraintAnnotation) {
        this.dateFormat = constraintAnnotation;
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        System.out.println(s);
        if (DateUtils.isValid(s, dateFormat.format())) {
            return true;
        }
        String template = constraintValidatorContext.getDefaultConstraintMessageTemplate();
        System.out.println("template："+template);
        constraintValidatorContext.disableDefaultConstraintViolation();
        //自定义消息返回
        constraintValidatorContext.buildConstraintViolationWithTemplate(template+"1234").addConstraintViolation();
        return false;
    }
}
