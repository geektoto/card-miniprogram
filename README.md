## CheckerFramework type system for Crypto Compliance

A Java compiler plugin that checks that no weak cipher algorithms are used with the Java crypto API.

### How does it work?

The checker builds on the Checker Framework (www.checkerframework.org), an open-source tool for building extensions to
the Java compiler's typechecker. A typechecker is perfect for checking a compliance rule, because typecheckers are
*sound*, meaning that they never miss errors, but might report false positives. In other words, a typechecker
over-approximates what your program might do at runtime, so if the checker reports tha