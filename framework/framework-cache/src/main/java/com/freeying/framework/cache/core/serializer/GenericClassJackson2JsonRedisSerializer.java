package com.freeying.framework.cache.core.serializer;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.freeying.common.core.jackson.JacksonJavaTimeModule;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.jsontype.impl.StdTypeResolverBuilder;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.SerializerFactory;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.cache.support.NullValue;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;


/**
 * Generic Jackson 2-based {@link RedisSerializer} that maps {@link Object objects} to JSON using dynamic typing.
 * <p>
 * 添加对Java8日期支持
 * </p>
 *
 * @author Christoph Strobl
 * @author Mark Paluch
 * @author Mao Shuai
 * @since 1.6
 */
public class GenericClassJackson2JsonRedisSerializer implements RedisSerializer<Object> {

    private static final byte[] EMPTY_ARRAY = new byte[0];

    private final ObjectMapper mapper;

    /**
     * Creates {@link org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer} and configures {@link ObjectMapper} for default typing.
     */
    public GenericClassJackson2JsonRedisSerializer() {
        this((String) null);
    }

    /**
     * Creates {@link org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer} and configures {@link ObjectMapper} for default typing using the
     * given {@literal name}. In case of an {@literal empty} or {@literal null} String the default
     * {@link JsonTypeInfo.Id#CLASS} will be used.
     *
     * @param classPropertyTypeName Name of the JSON property holding type information. Can be {@literal null}.
     * @see ObjectMapper#activateDefaultTypingAsProperty(PolymorphicTypeValidator, DefaultTyping, String)
     * @see ObjectMapper#activateDefaultTyping(PolymorphicTypeValidator, DefaultTyping, As)
     */
    public GenericClassJackson2JsonRedisSerializer(String classPropertyTypeName) {

        this(new ObjectMapper());

        // simply setting {@code mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)} does not help here since we need
        // the type hint embedded for deserialization using the default typing feature.
        registerNullValueSerializer(mapper, classPropertyTypeName);

        StdTypeResolverBuilder typer = new TypeResolverBuilder(DefaultTyping.EVERYTHING,
                mapper.getPolymorphicTypeValidator());
        typer = typer.init(JsonTypeInfo.Id.CLASS, null);
        typer = typer.inclusion(As.PROPERTY);

        if (StringUtils.hasText(classPropertyTypeName)) {
            typer = typer.typeProperty(classPropertyTypeName);
        }
        mapper.setDefaultTyping(typer);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    /**
     * Setting a custom-configured {@link ObjectMapper} is one way to take further control of the JSON serialization
     * process. For example, an extended {@link SerializerFactory} can be configured that provides custom serializers for
     * specific types.
     *
     * @param mapper must not be {@literal null}.
     */
    public GenericClassJackson2JsonRedisSerializer(ObjectMapper mapper) {

        Assert.notNull(mapper, "ObjectMapper must not be null!");
        this.mapper = mapper;
    }

    /**
     * Register {@link NullValueSerializer} in the given {@link ObjectMapper} with an optional
     * {@code classPropertyTypeName}. This method should be called by code that customizes
     * {@link org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer} by providing an external {@link ObjectMapper}.
     *
     * @param objectMapper          the object mapper to customize.
     * @param classPropertyTypeName name of the type property. Defaults to {@code @class} if {@literal null}/empty.
     * @since 2.2
     */
    public static void registerNullValueSerializer(ObjectMapper objectMapper, String classPropertyTypeName) {

        // simply setting {@code mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)} does not help here since we need
        // the type hint embedded for deserialization using the default typing feature.
        objectMapper.registerModules(
                new SimpleModule().addSerializer(new NullValueSerializer(classPropertyTypeName)),
                new JavaTimeModule(),
                new JacksonJavaTimeModule());
    }

    /**
     * @param source object to serialize. Can be {@literal null}.
     * @return byte
     * @see RedisSerializer#serialize(Object)
     */
    @Override
    public byte[] serialize(Object source) throws SerializationException {

        if (source == null) {
            return EMPTY_ARRAY;
        }

        try {
            return mapper.writeValueAsBytes(source);
        } catch (JsonProcessingException e) {
            throw new SerializationException("Could not write JSON: " + e.getMessage(), e);
        }
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.serializer.RedisSerializer#deserialize(byte[])
     */
    @Override
    public Object deserialize(byte[] source) throws SerializationException {
        return deserialize(source, Object.class);
    }

    private boolean isEmpty(byte[] data) {
        return (data == null || data.length == 0);
    }

    /**
     * @param source can be {@literal null}.
     * @param type   must not be {@literal null}.
     * @return {@literal null} for empty source.
     */
    public <T> T deserialize(byte[] source, Class<T> type) throws SerializationException {

        Assert.notNull(type,
                "Deserialization type must not be null! Please provide Object.class to make use of Jackson2 default typing.");

        if (isEmpty(source)) {
            return null;
        }

        try {
            return mapper.readValue(source, type);
        } catch (Exception ex) {
            throw new SerializationException("Could not read JSON: " + ex.getMessage(), ex);
        }
    }

    /**
     * {@link StdSerializer} adding class information required by default typing. This allows de-/serialization of
     * {@link NullValue}.
     *
     * @author Christoph Strobl
     * @since 1.8
     */
    private static class NullValueSerializer extends StdSerializer<NullValue> {

        private final String classIdentifier;

        /**
         * @param classIdentifier can be {@literal null} and will be defaulted to {@code @class}.
         */
        NullValueSerializer(String classIdentifier) {

            super(NullValue.class);
            this.classIdentifier = StringUtils.hasText(classIdentifier) ? classIdentifier : "@class";
        }

        /*
         * (non-Javadoc)
         * @see com.fasterxml.jackson.databind.ser.std.StdSerializer#serialize(java.lang.Object, com.fasterxml.jackson.core.JsonGenerator, com.fasterxml.jackson.databind.SerializerProvider)
         */
        @Override
        public void serialize(NullValue value, JsonGenerator jgen, SerializerProvider provider) throws IOException {

            jgen.writeStartObject();
            jgen.writeStringField(classIdentifier, NullValue.class.getName());
            jgen.writeEndObject();
        }

        @Override
        public void serializeWithType(NullValue value, JsonGenerator gen, SerializerProvider serializers,
                                      TypeSerializer typeSer) throws IOException {
            serialize(value, gen, serializers);
        }
    }

    /**
     * Custom {@link StdTypeResolverBuilder} that considers typing for non-primitive types. Primitives, their wrappers and
     * primitive arrays do not require type hints. The default {@code DefaultTyping#EVERYTHING} typing does not satisfy
     * those requirements.
     *
     * @author Mark Paluch
     * @since 2.7.2
     */
    private static class TypeResolverBuilder extends ObjectMapper.DefaultTypeResolverBuilder {

        public TypeResolverBuilder(DefaultTyping t, PolymorphicTypeValidator ptv) {
            super(t, ptv);
        }

        @Override
        public ObjectMapper.DefaultTypeResolverBuilder withDefaultImpl(Class<?> defaultImpl) {
            return this;
        }

        /**
         * Method called to check if the default type handler should be used for given type. Note: "natural types" (String,
         * Boolean, Integer, Double) will never use typing; that is both due to them being concrete and final, and since
         * actual serializers and deserializers will also ignore any attempts to enforce typing.
         */
        @Override
        public boolean useForType(JavaType t) {

            if (t.isJavaLangObject()) {
                return true;
            }

            t = resolveArrayOrWrapper(t);

            if (ClassUtils.isPrimitiveOrWrapper(t.getRawClass())) {
                return false;
            }

            // [databind#88] Should not apply to JSON tree models:
            return !TreeNode.class.isAssignableFrom(t.getRawClass());
        }

        private JavaType resolveArrayOrWrapper(JavaType type) {

            while (type.isArrayType()) {
                type = type.getContentType();
                if (type.isReferenceType()) {
                    type = resolveArrayOrWrapper(type);
                }
            }

            while (type.isReferenceType()) {
                type = type.getReferencedType();
                if (type.isArrayType()) {
                    type = resolveArrayOrWrapper(type);
                }
            }

            return type;
        }
    }
}
