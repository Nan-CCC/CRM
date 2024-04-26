package com.example.enterprisecrm.common.Log;

import java.lang.annotation.*;

//注解标注位置（方法，方法参数）
@Target({ElementType.METHOD,ElementType.PARAMETER})
//注解不仅被保存到class文件中，jvm加载class文件之后，仍然存在；
@Retention(RetentionPolicy.RUNTIME)
//标注，生成的javadoc文件会显示注解
@Documented
public @interface MyLog {

    //自定义模块名
    String title() default "";
    //方法传入的参数
    String optParam() default "";
    //操作类型
    BusniessType businessType() default BusniessType.OTHER;
}
