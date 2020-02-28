package com.kks.mydaggertest.di.auth;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by kaungkhantsoe on 2020-02-16.
 **/

@Scope
@Documented
@Retention(RUNTIME)
public @interface AuthScope {
}
