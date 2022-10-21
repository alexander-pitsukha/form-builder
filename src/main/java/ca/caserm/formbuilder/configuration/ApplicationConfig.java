package ca.caserm.formbuilder.configuration;

import ca.caserm.formbuilder.converter.DtoToFormTemplateConverter;
import ca.caserm.formbuilder.converter.FormTemplateToDtoConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.cache.SpringCacheBasedUserCache;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.TimeZone;

@Configuration
@EnableWebMvc
public class ApplicationConfig implements WebMvcConfigurer {

    /**
     * Create DefaultConversionService bean.
     *
     * @param converters List of Converter
     * @return DefaultConversionService
     */
    @Bean
    @DependsOn({DtoToFormTemplateConverter.COMPONENT_NAME, FormTemplateToDtoConverter.COMPONENT_NAME})
    @Autowired
    public DefaultConversionService defaultConversionService(final List<Converter<?, ?>> converters) {
        var defaultConversionService = new DefaultConversionService();
        converters.forEach(defaultConversionService::addConverter);
        return defaultConversionService;
    }

    /**
     * Create UserCache bean.
     *
     * @return UserCache
     */
    @Bean
    public UserCache userCache() {
        return new SpringCacheBasedUserCache(new ConcurrentMapCache(UserDetails.class.getName()));
    }

    /**
     * Create PasswordEncoder bean.
     *
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Set time zone for ObjectMapper.
     *
     * @param objectMapper ObjectMapper
     */
    @Autowired
    public void setTimeZone(final ObjectMapper objectMapper) {
        objectMapper.setTimeZone(TimeZone.getDefault());
    }

}
