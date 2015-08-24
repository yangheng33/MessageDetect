package com.detect.amar.common;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

import retrofit.converter.ConversionException;
import retrofit.converter.GsonConverter;
import retrofit.mime.MimeUtil;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

/**
 * Created by amar on 15/8/22.
 */
public class StringConverter extends GsonConverter {
    private Gson gson;
    private String charset;

    public StringConverter(Gson gson) {
        this(gson, "UTF-8");
    }

    public StringConverter(Gson gson, String charset) {
        super(gson, charset);
        this.gson = gson;
        this.charset = charset;
    }

    @Override
    public String fromBody(TypedInput body, Type type) throws ConversionException {
        String charset = this.charset;
        if (body.mimeType() != null) {
            charset = MimeUtil.parseCharset(body.mimeType(), charset);
        }
        InputStreamReader isr = null;
        try {
            isr = new InputStreamReader(body.in(), charset);

            char[] buffer = new char[1024];
            int readCount = 0;
            StringBuilder out = new StringBuilder();
            while ((readCount = isr.read(buffer)) > 0) {
                out.append(buffer,0,readCount);
            }

            return out.toString();
        } catch (IOException e) {
            throw new ConversionException(e);
        } catch (JsonParseException e) {
            throw new ConversionException(e);
        } finally {
            if (isr != null) {
                try {
                    isr.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    @Override
    public TypedOutput toBody(Object object) {
        return super.toBody(object);
    }
}
