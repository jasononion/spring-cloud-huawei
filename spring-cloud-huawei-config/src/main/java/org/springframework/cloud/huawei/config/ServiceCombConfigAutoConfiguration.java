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

package org.springframework.cloud.huawei.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.cloud.huawei.config.client.ServiceCombConfigClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author wangqijun
 * @Date 11:23 2019-10-17
 **/
@Configuration
@ConditionalOnServiceCombEnabled
@ConditionalOnProperty(name = "spring.cloud.servicecomb.config.enabled", matchIfMissing = true)
public class ServiceCombConfigAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean
  public ServiceCombConfigProperties serviceCombConfigProperties() {
    return new ServiceCombConfigProperties();
  }

  @Configuration
  protected static class RefreshConfiguration {

    @Bean
    @ConditionalOnProperty(name = "spring.cloud.servicecomb.config.watch.enabled",
        matchIfMissing = true)
    public ConfigWatch configWatch(ServiceCombConfigProperties serviceCombConfigProperties,
        ServiceCombConfigClient serviceCombConfigClient,
        ContextRefresher contextRefresher) {
      return new ConfigWatch(serviceCombConfigProperties, serviceCombConfigClient, contextRefresher);
    }
  }
}