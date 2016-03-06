package com.w9jds.marketbot.classes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Created by Jeremy on 3/5/2016.
 */
@Target(ElementType.FIELD)
public @interface StorageColumn {
    String value();
}
