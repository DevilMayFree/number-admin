package com.freeying.common.core.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Jackson工具类
 *
 * @author fx
 */
public final class JsonUtil {
    private static final Logger log = LoggerFactory.getLogger(JsonUtil.class);

    private JsonUtil() {
    }

    /**
     * 将对象序列化成json字符串
     *
     * @param value javaBean
     * @param <T>   T 泛型标记
     * @return jsonString json字符串
     */
    public static <T> String toJson(T value) {
        try {
            return JacksonHolder.getInstance().writeValueAsString(value);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return StringPool.EMPTY;
    }

    /**
     * 将对象序列化成json字符串
     *
     * @param objectMapper objectMapper
     * @param value        javaBean
     * @param <T>          T 泛型标记
     * @return jsonString json字符串
     */
    public static <T> String toJson(ObjectMapper objectMapper, T value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return StringPool.EMPTY;
    }

    /**
     * 将对象序列化成 json byte 数组
     *
     * @param object javaBean
     * @return jsonString json字符串
     */
    public static byte[] toJsonAsBytes(Object object) {
        try {
            return JacksonHolder.getInstance().writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }
        return new byte[]{};
    }

    /**
     * 将json反序列化成对象
     *
     * @param content   content
     * @param valueType class
     * @param <T>       T 泛型标记
     * @return Bean
     */
    public static <T> T parse(String content, Class<T> valueType) {
        try {
            return JacksonHolder.getInstance().readValue(content, valueType);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 将json反序列化成对象
     *
     * @param content       content
     * @param typeReference 泛型类型
     * @param <T>           T 泛型标记
     * @return Bean
     */
    public static <T> T parse(String content, TypeReference<T> typeReference) {
        try {
            return JacksonHolder.getInstance().readValue(content, typeReference);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 将json byte 数组反序列化成对象
     *
     * @param bytes     json bytes
     * @param valueType class
     * @param <T>       T 泛型标记
     * @return Bean
     */
    public static <T> T parse(byte[] bytes, Class<T> valueType) {
        try {
            return JacksonHolder.getInstance().readValue(bytes, valueType);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }


    /**
     * 将json反序列化成对象
     *
     * @param bytes         bytes
     * @param typeReference 泛型类型
     * @param <T>           T 泛型标记
     * @return Bean
     */
    public static <T> T parse(byte[] bytes, TypeReference<T> typeReference) {
        try {
            return JacksonHolder.getInstance().readValue(bytes, typeReference);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 将json反序列化成对象
     *
     * @param in        InputStream
     * @param valueType class
     * @param <T>       T 泛型标记
     * @return Bean
     */
    public static <T> T parse(InputStream in, Class<T> valueType) {
        try {
            return JacksonHolder.getInstance().readValue(in, valueType);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 将json反序列化成对象
     *
     * @param in            InputStream
     * @param typeReference 泛型类型
     * @param <T>           T 泛型标记
     * @return Bean
     */
    public static <T> T parse(InputStream in, TypeReference<T> typeReference) {
        try {
            return JacksonHolder.getInstance().readValue(in, typeReference);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 将json反序列化成List对象
     *
     * @param content      content
     * @param valueTypeRef class
     * @param <T>          T 泛型标记
     * @return List
     */
    public static <T> List<T> parseArray(String content, Class<T> valueTypeRef) {
        try {
            if (!StringUtils.startsWithIgnoreCase(content, StringPool.LEFT_SQ_BRACKET)) {
                content = StringPool.LEFT_SQ_BRACKET + content + StringPool.RIGHT_SQ_BRACKET;
            }
            List<Map<String, Object>> list = JacksonHolder.getInstance().readValue(content, new TypeReference<List<Map<String, Object>>>() {
            });
            List<T> result = new ArrayList<>();
            for (Map<String, Object> map : list) {
                result.add(toPojo(map, valueTypeRef));
            }
            return result;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    /**
     * 将json反序列化成List<Map>对象
     *
     * @param content content
     * @return Map
     */
    public static List<Map<String, Object>> toListMap(String content) {
        try {
            return JacksonHolder.getInstance().readValue(content, new TypeReference<>() {
            });
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return new ArrayList<>();
    }

    /**
     * 将json反序列化成Map对象
     *
     * @param content content
     * @return Map
     */
    public static Map<String, Object> toMap(String content) {
        try {
            return JacksonHolder.getInstance().readValue(content, new TypeReference<Map<String, Object>>() {
            });
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return new HashMap<>();
    }

    /**
     * 将json反序列化成Map对象
     *
     * @param objectMapper objectMapper
     * @param content      content
     * @return Map
     */
    public static Map<String, Object> toMap(ObjectMapper objectMapper, String content) {
        try {
            return objectMapper.readValue(content, new TypeReference<Map<String, Object>>() {
            });
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return new HashMap<>();
    }

    /**
     * 将json反序列化成Map对象
     *
     * @param content      content
     * @param valueTypeRef valueTypeRef
     * @param <T>          T 泛型标记
     * @return Map
     */
    public static <T> Map<String, T> toMap(String content, Class<T> valueTypeRef) {
        try {
            Map<String, Map<String, Object>> map = JacksonHolder.getInstance().readValue(content, new TypeReference<Map<String, Map<String, Object>>>() {
            });
            Map<String, T> result = new HashMap<>(16);
            for (Map.Entry<String, Map<String, Object>> entry : map.entrySet()) {
                result.put(entry.getKey(), toPojo(entry.getValue(), valueTypeRef));
            }
            return result;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return new HashMap<>();
    }

    @SuppressWarnings("rawtypes")
    public static <T> T toPojo(Map fromValue, Class<T> toValueType) {
        return JacksonHolder.getInstance().convertValue(fromValue, toValueType);
    }

    /**
     * 将json字符串转成 JsonNode
     *
     * @param jsonString jsonString
     * @return jsonString json字符串
     */
    public static JsonNode readTree(String jsonString) {
        try {
            return JacksonHolder.getInstance().readTree(jsonString);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 将json字符串转成 List JsonNode
     *
     * @param jsonString jsonString
     * @return jsonString json字符串
     */
    public static List<JsonNode> readTreeList(String jsonString) {
        try {
            return JacksonHolder.getInstance().readValue(jsonString, JacksonHolder.getTypeFactory().constructCollectionType(List.class, JsonNode.class));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return new ArrayList<>();
    }

    /**
     * 将json字符串转成 JsonNode
     *
     * @param in InputStream
     * @return jsonString json字符串
     */
    public static JsonNode readTree(InputStream in) {
        try {
            return JacksonHolder.getInstance().readTree(in);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 将json字符串转成 JsonNode
     *
     * @param content content
     * @return jsonString json字符串
     */
    public static JsonNode readTree(byte[] content) {
        try {
            return JacksonHolder.getInstance().readTree(content);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 将json字符串转成 JsonNode
     *
     * @param jsonParser JsonParser
     * @return jsonString json字符串
     */
    public static JsonNode readTree(JsonParser jsonParser) {
        try {
            return JacksonHolder.getInstance().readTree(jsonParser);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

}
