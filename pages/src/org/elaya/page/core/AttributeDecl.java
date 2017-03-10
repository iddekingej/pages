package org.elaya.page.core;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
public @interface AttributeDecl {
	Class<?> type() default void.class;
	boolean mandatory() default false;
}
