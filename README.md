## CheckerFramework type system for Crypto Compliance

A Java compiler plugin that checks that no weak cipher algorithms are used with the Java crypto API.

### How does it work?

The checker builds on the Checker Framework (www.checkerframework.org), an open-source tool for building extensions to
the Java compiler's typechecker. A typechecker is perfect for checking a compliance rule, because typecheckers are
*sound*, meaning that they never miss errors, but might report false positives. In other words, a typechecker
over-approximates what your program might do at runtime, so if the checker reports that the code is safe, you can be
confident that it is. If the checker issues an error, there are two possibilities:

1. there is a real issue: you are using a crypto algorithm that is not whitelisted in the stubs folder, OR
2. the type system cannot understand what algorithm you are trying to use. This happens if you load the algorithm
from a file, or wrap it in enums. Complicated code like this is usually discouraged, but you can add an exception as
described in the section below.

## How do I run it?

The CheckerFramework provides different build system integrations that are described on their wiki. For a quick start, try running it on a single file:

```
./gradlew assemble
./gradlew copyDependencies

javac -cp ./build/libs/aws-crypto-policy-compliance-checker.jar:dependencies/checker-3.1.1.jar -processor com.amazon.checkerframework.cryptopolicy.CryptoPolicyComplianceChecker -AstubDebug -Astubs=stubs/java.security.astub:stubs/javax.crypto.astub tests/crypto-policy/CryptoTests.java
```

The output should read sth like:

```
Note: StubParser: parsing stub file statically-executable.astub
Note: StubParser: parsing stub file /Users/schaef/workspace/crypto/src/CryptoPolicyComplianceChecker/stubs/java.security.astub
Note: StubParser: parsing stub file /Users/schaef/workspace/crypto/src/CryptoPolicyComplianceChecker/stubs/javax.crypto.astub
warning: You do not seem to be using the distributed annotated JDK.  To fix the problem, supply javac an argument like:  -Xbootclasspath/p:.../checker/dist/ .  Currently using: jdk8.jar
tests/crypto-policy/CryptoTests.java:39: error: [bad.crypto.issue.url] Please provide a valid URL that justifies the use of algorithm DES.
    static void suppressedWarningsWithBadURL() throws Exception {
                ^
  	badurl
  is not a valid URL.
tests/crypto-policy/CryptoTests.java:23: error: [crypto.policy.violation] Used crypto algorithm: DEFAULT/PADDING is not strong enough. Consider using a stronger algorithm such as RSA/ECB/OAEPP