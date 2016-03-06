package com.w9jds.marketbot.classes.utils;

import android.database.Cursor;

import com.w9jds.marketbot.classes.StorageColumn;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by Jeremy on 3/5/2016.
 */
public final class StorageUtils {

    public static <T> T buildClass(T object, Cursor cursor) {
        try {
            Class type = object.getClass();

            ArrayList<Field> fields = new ArrayList<>();
            fields.addAll(Arrays.asList(type.getDeclaredFields()));
            if (type.getSuperclass() != Object.class) {
                fields.addAll(Arrays.asList(type.getSuperclass().getDeclaredFields()));
            }

            for (Field field : fields) {
                if (field.isAnnotationPresent(StorageColumn.class)) {
                    String columnName = field.getAnnotation(StorageColumn.class).value();
                    int columnIndex = cursor.getColumnIndex(columnName);

                    if (columnIndex > -1) {
                        if (field.getType() == String.class) {
                            field.set(object, cursor.getString(columnIndex));
                        }
                        if (field.getType() == long.class) {
                            field.set(object, cursor.getLong(columnIndex));
                        }
                        if (field.getType() == double.class) {
                            field.set(object, cursor.getDouble(columnIndex));
                        }
                        if (field.getType() == Date.class) {
                            long date = cursor.getLong(columnIndex);
                            field.set(object, new Date(date));
                        }
                    }
                }
            }
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }

        return object;
    }

}
