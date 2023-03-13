//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.yangxinyu.test;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Objects;
import java.util.Optional;

public class JsonUtils {
    private static ObjectMapper MAPPER = new ObjectMapper();

    public JsonUtils() {
    }

    public static void setMapper(ObjectMapper mapper) {
        Objects.requireNonNull(mapper);
        MAPPER = mapper;
    }

    public static ObjectMapper getMapper() {
        return MAPPER;
    }

    public static String toJsonString(Object object) {
        if (object == null) {
            return null;
        } else if (object instanceof String) {
            return (String)object;
        } else {
            try {
                return MAPPER.writeValueAsString(object);
            } catch (JsonProcessingException var2) {
                throw new RuntimeException("JSON序列化失败", var2);
            }
        }
    }

    public static <T> T parseObject(String json, Class<T> clz) {
        try {
            return MAPPER.readValue(json, clz);
        } catch (IOException var3) {
            throw new RuntimeException("JSON反序列化失败", var3);
        }
    }

    public static <T> T parseObject(String json, JavaType type) {
        try {
            return MAPPER.readValue(json, type);
        } catch (IOException var3) {
            throw new RuntimeException("JSON反序列化失败", var3);
        }
    }

    public static <T> T convert(Object srcValue, Type type) {
        return (T) convert(srcValue, type == null ? null : MAPPER.constructType(type));
    }

    public static Object convert(Object srcValue, JavaType type) {
        if (type == null) {
            return srcValue;
        } else {
            Object tarValue = srcValue;
            if (srcValue != null && srcValue instanceof Optional) {
                Optional<?> o = (Optional)srcValue;
                if (o.isPresent()) {
                    tarValue = o.get();
                } else {
                    tarValue = null;
                }
            }

            Class<?> raw = type.getRawClass();
            if (raw == Optional.class) {
                if (tarValue == null) {
                    return Optional.empty();
                } else {
                    tarValue = MAPPER.convertValue(tarValue, type.containedType(0));
                    return tarValue == null ? Optional.empty() : Optional.of(tarValue);
                }
            } else {
                return tarValue == null ? null : MAPPER.convertValue(tarValue, type);
            }
        }
    }

    public static JsonNode toJsonNode(String json) {
        try {
            return getMapper().readTree(json);
        } catch (IOException var2) {
            throw new RuntimeException("解析失败");
        }
    }

    public static <T> T readObject(JsonNode node, Class<T> clz) {
        try {
            return getMapper().treeToValue(node, clz);
        } catch (IOException var3) {
            throw new RuntimeException("解析失败");
        }
    }

    static {
        MAPPER.setSerializationInclusion(Include.NON_NULL);
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
}
