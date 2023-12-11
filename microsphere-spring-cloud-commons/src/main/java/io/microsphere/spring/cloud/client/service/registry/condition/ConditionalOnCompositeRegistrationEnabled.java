package io.microsphere.spring.cloud.client.service.registry.condition;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static io.microsphere.spring.cloud.commons.constants.CommonsPropertyConstants.COMPOSITE_REGISTRATION_ENABLED_PROPERTY_NAME;

/**
 * @author <a href="mailto:maimengzzz@gmail.com">韩超</a>
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@ConditionalOnProperty(COMPOSITE_REGISTRATION_ENABLED_PROPERTY_NAME)
public @interface ConditionalOnCompositeRegistrationEnabled {

}
