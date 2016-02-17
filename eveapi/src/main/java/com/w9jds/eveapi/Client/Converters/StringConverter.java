package com.w9jds.eveapi.Client.Converters;

import java.lang.reflect.Type;
import java.util.Scanner;

import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

/**
 * Created by Jeremy Shore on 2/16/16.
 */
public final class StringConverter implements Converter {

    @Override
    public Object fromBody(TypedInput typedInput, Type type) throws ConversionException {
        try {
            Scanner scanner = new Scanner(typedInput.in()).useDelimiter("\\A");
            return scanner.hasNext() ? scanner.next() : "";
        }
        catch(Exception ex) {
            ex.getCause().printStackTrace();
            return null;
        }
    }

    @Override
    public TypedOutput toBody(Object o) {
        return null;
    }
}