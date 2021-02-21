package com.appgate.api.calculator.interceptors;

import com.appgate.api.calculator.models.TransactionActions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD})
public @interface TransactionLogger {

    TransactionActions action();
}
