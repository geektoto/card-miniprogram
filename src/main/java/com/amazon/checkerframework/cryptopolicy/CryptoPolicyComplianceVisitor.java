package com.amazon.checkerframework.cryptopolicy;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.amazon.checkerframework.cryptopolicy.qual.CryptoBlackListed;
import com.amazon.checkerframework.cryptopolicy.qual.CryptoWhiteListed;
import com.amazon.checkerframework.cryptopolicy.qual.SuppressCryptoWarning;
import com.sun.source.tree.ClassTree;
import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.VariableTree;
import com.sun.source.util.TreePath;
import org.checkerframework.checker.compilermsgs.qual.CompilerMessageKey;
import org.checkerframework.common.basetype.BaseTypeChecker;
import org.checkerframework.common.basetype.BaseTypeVisitor;
import org.checkerframework.common.value.ValueAnnotatedTypeFactory;
import org.checkerframework.common.value.ValueChecker;
import org.checkerframework.common.value.qual.StringVal;
import org.checkerframework.framework.source.Result;
import org.checkerframework.framework.type.AnnotatedTypeMirror;
import org.checkerframework.javacutil.AnnotationUtils;
import org.checkerframework.javacutil.TreeUtils;

/**
 * Modifies the common assignment check to permit values with StringVal annotations from
 * the Value Checker to be assigned to lhs_s that require a crypto whitelist.
 */
public class CryptoPolicyComplianceVisitor extends BaseTypeVisitor {

    private static final @CompilerMessageKey String CRYPTO_COMPLIANCE_WARNING_KEY = "crypto.policy.warning";
    private static final @CompilerMessageKey String CRYPTO_COMPLIANCE_ERROR_KEY = "crypto.policy.violation";
    private static final @CompilerMessageKey String BAD_URL_KEY = "bad.crypto.issue.url";
    private static final @CompilerMessageKey String UNKNOWN_ALGORITHM_KEY = "crypto.cipher.unknown";

    /**
     * Default constructor.
     *
     * @param checker Provided by the checker framework
     */
    public CryptoPolicyComplianceVisitor(BaseTypeChecker checker) {
        super(checker);
    }

    /**
     * Changes how assignments and pseudo-assignments are resolved. lhsType is the type of the lhs expression,
     * rhsTree is the rhs of the assignment.
     *
     * @param lhsType  lhs type of the assignment
     * @param rhsTree  rhs tree of the assingment
     * @param errorKey key of the error to issue if this check fails.
     */
    @Override
    public void commonAssignmentCheck(final AnnotatedTypeMirror lhsType,
                                      final ExpressionTree rhsTree,
                                      final String errorKey) {

        final List<String> stringValAnnotations = getLowerCasedStringValAnnotations(rhsTree);
        final AnnotationMirro