import javax.crypto.Cipher;

/**
 * The type checker current does not infer types across method boundaries. Hence,
 * both methods throw a type error. The method falsePositive should not throw an error
 * since know that it has only one calling context which is save. However, this assumes that our
 * program is "closed" (i.e., its not a library and we know all calling contexts).
 */
public class Precision {

    private static final String BAD_CIPHER = "des";
    private static final String GOOD_CIPHER = "AESWrap";

    static void shouldFail() throws Exception {

        badString("passing