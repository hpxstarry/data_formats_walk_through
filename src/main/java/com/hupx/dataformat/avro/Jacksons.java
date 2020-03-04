package com.hupx.dataformat.avro;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ser.FilterProvider;
import org.codehaus.jackson.map.ser.impl.SimpleBeanPropertyFilter;
import org.codehaus.jackson.map.ser.impl.SimpleFilterProvider;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

public class Jacksons {

    private ObjectMapper objectMapper;

    public static Jacksons one() {
        return new Jacksons();
    }

    private Jacksons() {
        objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        //objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true); only supported in jackson2
        //formatter features
        objectMapper.configure(JsonGenerator.Feature.QUOTE_FIELD_NAMES, false);
        //parser features
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS, true);

    }

    public Jacksons filter(String filterName, String... properties) {
        FilterProvider filterProvider = new SimpleFilterProvider().addFilter(filterName,
                SimpleBeanPropertyFilter.serializeAllExcept(properties));
        objectMapper.setFilters(filterProvider);
        return this;
    }

    public Jacksons addMixInAnnotations(Class<?> target, Class<?> mixinSource) {
        objectMapper.getSerializationConfig().addMixInAnnotations(target, mixinSource);
        objectMapper.getDeserializationConfig().addMixInAnnotations(target, mixinSource);
        return this;
    }

    public Jacksons setDateFormate(DateFormat dateFormat) {
        objectMapper.setDateFormat(dateFormat);
        return this;
    }

    public <T> T json2Obj(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public String writeToString(Object obj) {
        return this.writeToString(obj, false);
    }

    public String toPrettyString(Object obj) {
        return this.writeToString(obj, true);
    }

    public String writeToString(Object obj, boolean prettyFormat) {
        try {
            if (prettyFormat) {
                objectMapper.writerWithDefaultPrettyPrinter();
            }
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> json2List(String json) {
        try {
            return objectMapper.readValue(json, List.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
