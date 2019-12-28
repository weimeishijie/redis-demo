package com.redis.util;


import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.redis.enmu.TimeZone;

/**
 * Created by mj on 2018/1/20.
 *
 */
public class JSR310DateTimeSerializer  extends JsonSerializer<TemporalAccessor>{

    private static final DateTimeFormatter ISOFormatter =
            DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(TimeZone.ASIA_SHANGHAI.getId());

    public static final JSR310DateTimeSerializer INSTANCE = new JSR310DateTimeSerializer();

    private JSR310DateTimeSerializer() {
    }

    @Override
    public void serialize(TemporalAccessor temporalAccessor, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException, JsonProcessingException {
        jsonGenerator.writeString(ISOFormatter.format(temporalAccessor));
    }


}
