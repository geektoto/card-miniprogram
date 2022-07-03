package com.amazon.checkerframework.cryptopolicy;

import javax.lang.model.element.AnnotationMirror;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Set;

import com.amazon.checkerframework.cryptopolicy.qual.CryptoBlackListed;
import com.amazon.checkerframework.cryptopolicy.qual.CryptoPolicyBottom;
import com.amazon.checkerframework.cryptopolicy.qual.CryptoWhiteListed;
import com.amazon.checkerframework.cryptopolicy.qual.UnknownCryptoAlgorithm;
import org.checkerframework.common.basetype.BaseAnnotatedTypeFactory;
import org.checkerframework.common.basetype.BaseTypeChecker;
import org.checkerframework.framework.util.MultiGraphQualifierHierarchy;
import org.checkerframework.framework.util.MultiGraphQua