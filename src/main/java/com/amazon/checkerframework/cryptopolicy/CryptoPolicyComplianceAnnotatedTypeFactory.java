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
import org.checkerframework.framework.util.MultiGraphQualifierHierarchy.MultiGraphFactory;
import org.checkerframework.javacutil.AnnotationUtils;

/**
 * Need this to define the subtyping relationship between @CryptoWhiteListed annotations.
 */
public class CryptoPolicyComplianceAnnotatedTypeFactory extends BaseAnnotatedTypeFactory {

    /**
     * Default constructor.
     * @param checker from the CF.
     */
    public CryptoPolicyComplianceAnnotatedTypeFactory(final BaseTypeChecker checker) {
        super(checker);
        this.postInit();
    }


    @Override
    protected Set<Class<? extends Annotation>> createSupportedTypeQualifiers() {
        return getBundledTypeQualifiers(CryptoBlackListed.class,
                CryptoPolicyBottom.class, CryptoWhiteListed.class, UnknownCryptoAlgorithm.class);
    }

    @Override
    public MultiGraphQualifierHierarchy createQualifierHierarchy(final MultiGraphFactory f) {
        return new CryptoPolicyComplianceQualifierHierarchy(f);
    }

    /**
     * A custom qualifier hierarchy to handle subtyping.
     */
    private static class CryptoPolicyComplianceQualifierHierarchy extends MultiGraphQualifierHierarchy {
        /**
         * Constructor matching super
         * @param f supplied by CF
         */
        CryptoPolicyComplianceQualifierHierarchy(final MultiGraphFactory f) {
            super(f);
        }

        @Override
        public boolean isSubtype(final AnnotationMirror subtype, final AnnotationMirror supertype) {
            if (AnnotationUtils.areSameByClass(supertype, UnknownCryptoAlgorithm.class)
                    || AnnotationUtils.areSameByClass(subtype, CryptoPolicyBottom.class)) {
                return true;
            } else if (AnnotationU