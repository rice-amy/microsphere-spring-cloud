/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.microsphere.spring.cloud.client.service.registry.autoconfigure;

import io.microsphere.spring.cloud.client.service.registry.MultipleAutoServiceRegistration;
import io.microsphere.spring.cloud.client.service.registry.MultipleRegistration;
import io.microsphere.spring.cloud.client.service.registry.MultipleServiceRegistry;
import io.microsphere.spring.cloud.client.service.registry.RegistrationCustomizer;
import io.microsphere.spring.cloud.client.service.registry.aspect.EventPublishingRegistrationAspect;
import io.microsphere.spring.cloud.client.service.registry.condition.ConditionalOnAutoServiceRegistrationEnabled;
import io.microsphere.spring.cloud.client.service.registry.condition.ConditionalOnMultipleRegistrationEnabled;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cloud.client.serviceregistry.AutoServiceRegistrationProperties;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.cloud.client.serviceregistry.ServiceRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

import java.util.stream.Collectors;

/**
 * Auto-Configuration class for {@link ServiceRegistry ServiceRegistry}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 * @since 1.0.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnAutoServiceRegistrationEnabled
@Import(value = {
        EventPublishingRegistrationAspect.class,
        ServiceRegistryAutoConfiguration.MultipleRegistrationConfiguration.class
})
public class ServiceRegistryAutoConfiguration {


    @ConditionalOnMultipleRegistrationEnabled
    public static class MultipleRegistrationConfiguration {

        @Primary
        @Bean
        public MultipleRegistration multipleRegistration(@Value("${microsphere.spring.cloud.default-registration.type}") Class<? extends Registration> defaultRegistrationClass,
                                                         ObjectProvider<Registration> registrationProvider) {
            return new MultipleRegistration(defaultRegistrationClass, registrationProvider.stream().collect(Collectors.toList()));
        }

        @Primary
        @Bean
        public MultipleServiceRegistry multipleServiceRegistry(@Value("${microsphere.spring.cloud.default-service-registry.type}") Class<? extends ServiceRegistry> defaultServiceRegistryClass,
                                                               ObjectProvider<ServiceRegistry> serviceRegistries,
                                                               ObjectProvider<RegistrationCustomizer> registrationCustomizers) {
            return new MultipleServiceRegistry(defaultServiceRegistryClass, serviceRegistries.stream().collect(Collectors.toList()), registrationCustomizers);
        }

        @ConditionalOnBean(AutoServiceRegistrationProperties.class)
        @Primary
        @Bean
        public MultipleAutoServiceRegistration multipleAutoServiceRegistration(
                MultipleRegistration multipleRegistration,
                MultipleServiceRegistry serviceRegistry,
                AutoServiceRegistrationProperties properties) {
            return new MultipleAutoServiceRegistration(multipleRegistration, serviceRegistry, properties);
        }
    }

}
