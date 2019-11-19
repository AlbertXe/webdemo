package com.aop;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = DateFormatValidator.class)
public @interface DateFormat {
    String message() default "日期格式不正确";
    String format() default "yyyyMMdd";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
