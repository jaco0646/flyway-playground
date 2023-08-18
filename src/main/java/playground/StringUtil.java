package playground;

import static java.util.Arrays.stream;

public class StringUtil {
    private StringUtil() {}

    public static boolean anyMissing(String... s) {
        return stream(s).anyMatch(StringUtil::isMissing);
    }

    public static boolean isMissing(String s) {
        return s == null || s.isBlank();
    }
}
