package com.aop;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DateFormatValidator implements ConstraintValidator<DateFormat,String> {
    private String format;
    @Override
    public void initialize(DateFormat constraintAnnotation) {
        this.format = constraintAnnotation.format();
        System.out.println("format:"+format);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        System.out.println(s);
        if (s.length() == 8) {
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
