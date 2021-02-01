package pl.tomek.utils;

import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class SubstringUtilsTest {

    @ParameterizedTest
    @MethodSource("testDataStreamSourceForTruePath")
    public void checkSetStringWithValidSubstring(String string, String substring) {
        Assertions.assertTrue(SubstringUtils.isSubstring(string, substring));
    }

    @ParameterizedTest
    @MethodSource("testDataStreamSourceForFalsePath")
    public void checkSetStringWithInvalidSubstring(String string, String substring) {
        Assertions.assertFalse(SubstringUtils.isSubstring(string, substring));
    }

    static Stream<Arguments> testDataStreamSourceForTruePath() {
        return Stream.of(
            arguments("abcd", "ab"),
            arguments("abcd", "bc"),
            arguments("abcd", "b*c"),
            arguments("abcd", "*bc*"),
            arguments("abcd", "******b*******c***********"),
            arguments("abcd", "*"),
            arguments("abcd", "*b*"),
            arguments("abcd", "*******b**********c*****************"),
            arguments("bcbcbcbcbcbcbc", "bc*c*"),
            arguments("bcbcbcbcbcbcbc", "cbcbcb"),
            arguments("bcbcbcbcbcbcbc", "bcbcbcbcbcbcbc*"),
            arguments("bcbcbcbcbcbcbc", "*****bcbcbcbcbcbcbc*****"),
            arguments("bcbcbcbcbcbcbc", "*****bcbc*bcb*cbc*b*cbc*****"),
            arguments("bcbcbcbcbcbcbc", "*****bc******b******c*****bc********bcbcbcbc*****"),
            arguments("bcbcbcbcbcbcbc", "*****b*c*b*c*b*c*b*c*b*c*b*c*b*c*****"),
            arguments("bcbcbcbcbcbcbc", "*****b***c***b***c***b***c***b***c***b***c***b***c***b***c*****"),
            arguments("abcdefg", "ab*fg"),
            arguments("ab\\cdefg", "ab*fg"),
            arguments("ab****\\*c**d*efg", "ab*fg"),
            arguments("ab****\\*c**d*efg", "*ab*fg*"),
            arguments("ab*fg", "ab\\*fg"),
            arguments("abcdeabcdef", "abcde"),
            arguments("ab\\\\cdefg", "ab\\\\cdef"),
            arguments("ab\\\\*cdefgh", "ab\\\\\\*cdef"),
            arguments("it is very long long long long long long test", "it * test"),
            arguments("//*/\\", "/*/\\"),
            arguments("a * rr !", "a \\** !"),
            arguments(" ", ""),
            arguments("dynamic", ""),
            arguments("", "*"),
            arguments("***", "\\**\\*\\*"),
            arguments("a*aaa*a", "*a\\*"),
            arguments("\\\\", "\\"),
            arguments("lorem ipsum", " i*m"),
            arguments("123*45", "1*5"),
            arguments("\\\\\\", "\\"),
            arguments("*1", "\\*"),
            arguments("arr!", "*!"),
            arguments(" arr ", " "),
            arguments("***", "\\*"),
            arguments("abcd", "a*c")
        );
    }

    static Stream<Arguments> testDataStreamSourceForFalsePath() {
        return Stream.of(
            arguments("abcd", "*******b**********d**********c****************"),
            arguments("abcd", "*******b**********d**********c"),
            arguments("bcbcbcbcbcbcbc", "bbcbcbcb"),
            arguments("bcbcbcbcbcbcbc", "*b*cbccbc*"),
            arguments("bcbcbcbcbcbcbc", "***c**b***c***b***c***b***c***b***c***b***c***b***c***b***c*****"),
            arguments("bcbcbcbcbcbcbc", "*****b***c***b***c***b***c***b***c***b***c***b***c***b***c*b****"),
            arguments("bcbcbcbcbcbcbc", "*******b**********d**********c****************"),
            arguments("abcdefg", "ab\\*fg"),
            arguments("ab\\*fg", "ab\\*fg"),
            arguments("ab*fg", "ab\\*fg\\*"),
            arguments(null, null),
            arguments("any", null),
            arguments(null, "any"),
            arguments(null, ""),
            arguments("", null),
            arguments(null, ""),
            arguments(null, "*"),
            arguments("123", "\\*"),
            arguments("123", "\\**"),
            arguments("\\\\\\", "\\*"),
            arguments("zAq!@wSXxq\\", "q!*Q"),
            arguments("a*bcd\\", "a\\*c"),
            arguments("a*bcd\\", "\\*d\""),
            arguments("a*bcd\\", "a\\*d"),
            arguments("lorem ipsum", "ipsum "),
            arguments(" dy\\*nami c", "*dy*\\*namic"),
            arguments(" arr ", "  "),
            arguments("bcd", "cb")
        );
    }
}
