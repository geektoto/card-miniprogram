package com.amazon.checkerframework.cryptopolicy;

import java.util.LinkedHashSet;

import org.checkerframework.common.basetype.BaseTypeChecker;
import org.checkerframework.common.value.ValueChecker;

/**
 * Dummy class used by the checker framework to load the type factory.
 * Note that the CheckerFramework uses convention driven development by searching for a (x)Checker class and then
 * loading the (x)AnnotatedTypeFactory.
 * The checker does not