/*
 * Copyright 2016 Wojciech Zankowski.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.blazarquant.bfp.core.security;

import com.blazarquant.bfp.core.security.config.BcryptCredentialsMatcher;
import com.blazarquant.bfp.core.security.config.DatabaseUserRealm;
import com.blazarquant.bfp.core.security.config.FixedCookieRememberMeManager;
import com.blazarquant.bfp.core.security.config.ShiroMethodInterceptor;
import com.blazarquant.bfp.core.security.util.SettingsManager;
import com.blazarquant.bfp.data.user.Role;
import com.blazarquant.bfp.web.util.BlazarURL;
import com.google.inject.Key;
import com.google.inject.Singleton;
import com.google.inject.binder.AnnotatedBindingBuilder;
import com.google.inject.matcher.Matchers;
import com.google.inject.name.Names;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.guice.web.ShiroWebModule;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.session.mgt.ServletContainerSessionManager;

import javax.servlet.ServletContext;

/**
 * @author Wojciech Zankowski
 */
public class SecurityModule extends ShiroWebModule {

    public SecurityModule(ServletContext servletContext) {
        super(servletContext);
    }

    @Override
    protected void configureShiroWeb() {
        bind(CredentialsMatcher.class).to(BcryptCredentialsMatcher.class);
        bindRealm().to(DatabaseUserRealm.class).asEagerSingleton();

        bindConstant().annotatedWith(Names.named("shiro.sessionMode")).to("http");
        bindConstant().annotatedWith(Names.named("shiro.loginUrl")).to(BlazarURL.SIGNIN_URL);

        bindInterceptor(Matchers.any(), Matchers.annotatedWith(RequiresAuthentication.class),
                new ShiroMethodInterceptor());
        bind(RememberMeManager.class).to(FixedCookieRememberMeManager.class).in(Singleton.class);

        bind(CacheManager.class).to(EhCacheManager.class);

        addFilterChain(BlazarURL.HOME_URL, ANON);
        addFilterChain(BlazarURL.PARSER_URL, ANON);
        addFilterChain(BlazarURL.FILEPARSER_URL, ANON);
        addFilterChain(BlazarURL.FAQ_URL, ANON);
        addFilterChain(BlazarURL.SIGNUP_URL, ANON);
        addFilterChain(BlazarURL.SIGNIN_URL, ANON);

        addFilterChain(BlazarURL.HISTORY_URL, AUTHC);
        addFilterChain(BlazarURL.PROFILE_URL, AUTHC);
        addFilterChain(BlazarURL.SUBSCRIPTION_URL, AUTHC);
        addFilterChain(BlazarURL.PAYMENT_URL, AUTHC);

        addFilterChain(BlazarURL.ADMIN_URL, AUTHC, new RolesAuthorizationConfigKey(Role.ADMIN_ROLE.getName()));
    }

    @Override
    protected void bindSessionManager(AnnotatedBindingBuilder<SessionManager> bind) {
        bind.to(ServletContainerSessionManager.class).in(Singleton.class);
    }

    @Override
    protected void bindWebSecurityManager(AnnotatedBindingBuilder<? super WebSecurityManager> bind) {
        super.bindWebSecurityManager(bind);
    }

    /**
     * Generics not supported in Guice 4
     * // https://issues.apache.org/jira/browse/SHIRO-493
     */
    private static class RolesAuthorizationConfigKey extends FilterConfigKey<RolesAuthorizationFilter> {
        private RolesAuthorizationConfigKey(String configValue) {
            super(ROLES, configValue);
        }
    }

    private static class FilterConfigKey<T extends PathMatchingFilter> extends Key<T> {
        private Key<T> key;
        private String configValue;

        private FilterConfigKey(Key<T> key, String configValue) {
            super();
            this.key = key;
            this.configValue = configValue;
        }

        public Key<T> getKey() {
            return key;
        }

        public String getConfigValue() {
            return configValue;
        }
    }
}
