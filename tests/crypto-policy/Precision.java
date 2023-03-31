import javax.crypto.Cipher;

/**
 * The type checker current does not infer types across method boundaries. Hence,
 * both methods throw a type error. The method falsePositive should not throw an error
 * since know that it has only one calling context which 