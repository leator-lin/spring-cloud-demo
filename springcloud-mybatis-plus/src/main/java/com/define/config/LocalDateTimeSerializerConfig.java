package com.define.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sun.istack.internal.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/** Converter 不可优化使用Lambda表达式，否则会出现启动失败的问题 */
@Configuration
public class LocalDateTimeSerializerConfig {

  /** String --> LocalDate */
  @Bean
  public Converter<String, LocalDate> localDateConverter() {
    return new Converter<String, LocalDate>() {
      @Override
      public LocalDate convert(@NotNull String source) {
        if (StringUtils.hasText(source)) {
          return LocalDate.parse(source, DateTimeFormatter.ISO_OFFSET_DATE);
        }
        return null;
      }
    };
  }

  /** String --> LocalDatetime */
  @Bean
  public Converter<String, LocalDateTime> localDateTimeConverter() {
    return new Converter<String, LocalDateTime>() {
      @Override
      public LocalDateTime convert(@NotNull String source) {
        if (StringUtils.hasText(source)) {
          return LocalDateTime.parse(source, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.CHINA));
        }
        return null;
      }
    };
  }

  /** String --> LocalTime */
  @Bean
  public Converter<String, LocalTime> localTimeConverter() {
    return new Converter<String, LocalTime>() {
      @Override
      public LocalTime convert(@NotNull String source) {
        if (StringUtils.hasText(source)) {
          return LocalTime.parse(source, DateTimeFormatter.ISO_OFFSET_TIME);
        }
        return null;
      }
    };
  }

  /** Json序列化和反序列化转换器，用于转换Post请求体中的json以及将我们的对象序列化为返回响应的json */
  @Bean
  public ObjectMapper objectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    objectMapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);

    // LocalDateTime系列序列化模块，继承自jsr310，我们在这里修改了日期格式
    JavaTimeModule javaTimeModule = new JavaTimeModule();
    javaTimeModule.addSerializer(
        LocalDateTime.class,
        new JsonSerializer<LocalDateTime>() {
          @Override
          public void serialize(
              LocalDateTime value, JsonGenerator gen, SerializerProvider serializers)
              throws IOException {
            String format =
                value.atZone(ZoneOffset.UTC).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.CHINA));
            gen.writeString(format);
          }
        });

    javaTimeModule.addSerializer(
        LocalDate.class,
        new JsonSerializer<LocalDate>() {
          @Override
          public void serialize(LocalDate value, JsonGenerator gen, SerializerProvider serializers)
              throws IOException {
            String format = value.format(DateTimeFormatter.ISO_OFFSET_DATE);
            gen.writeString(format);
          }
        });

    objectMapper.registerModule(javaTimeModule);
    return objectMapper;
  }
}