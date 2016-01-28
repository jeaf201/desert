package com.bestfuture.desert.commons.dcd.api.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Excel Column Annotation
 * @author David
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelColumn {
	/**
	 * Excel列名
	 * @return
	 */
    String name() default "";
    
    /**
     * Excel列宽
     * @return
     */
    int width() default 20;
    
    /**
     * Excel数据格式
     * @return
     */
    String format() default "";
}
