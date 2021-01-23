package pl.tomek.utils;

public class SubstringUtils {
    private static final char ANY_STRING_INDICATOR = '*';
    private static final char ANY_STRING_ESCAPE_INDICATOR = '\\';

    private SubstringUtils() {
    }

    public static boolean isSubstring(String string, String substring) {
        char[] stringChars = string.toCharArray();
        char[] substringChars = substring.toCharArray();

        int stringLength = stringChars.length;
        int substringLength = substringChars.length;

        int substrCharsCurrentIndex = 0;

        boolean currentlyMatches = false;
        boolean isAnyCharMode = false;

        for (int i = 0; i < stringLength; i++) {
            char currentStringChar = stringChars[i];
            char currentSubstringChar = substringChars[substrCharsCurrentIndex];

            //handle with escape indicator
            if (currentSubstringChar == ANY_STRING_ESCAPE_INDICATOR && substrCharsCurrentIndex < substringLength - 1 && substringChars[substrCharsCurrentIndex + 1] == ANY_STRING_INDICATOR) {
                substrCharsCurrentIndex++;
                currentSubstringChar = substringChars[substrCharsCurrentIndex];
            }

            //handle with any string indicator
            if (currentSubstringChar == ANY_STRING_INDICATOR && (substrCharsCurrentIndex == 0 || substringChars[substrCharsCurrentIndex - 1] != ANY_STRING_ESCAPE_INDICATOR)) {
                if (substrCharsCurrentIndex == substringLength - 1) {
                    return true;
                } else {
                    for (int j = substrCharsCurrentIndex + 1; j < substringLength; j++) {
                        if (j == substringLength - 1) {
                            return true;
                        }

                        if (substringChars[j] != ANY_STRING_INDICATOR) {
                            substrCharsCurrentIndex = j;
                            currentSubstringChar = substringChars[substrCharsCurrentIndex];
                            isAnyCharMode = true;
                            break;
                        }
                    }
                }
            }

            //handle process in any char mode (escape asterisk detected)
            if (isAnyCharMode) {
                if (currentStringChar == currentSubstringChar) {
                    if (substrCharsCurrentIndex == substringLength - 1) {
                        return true;
                    } else {
                        substrCharsCurrentIndex++;
                        currentlyMatches = true;
                    }
                    isAnyCharMode = false;
                }
            //handle with normal matching mode
            } else {
                //proceed with next character when previous matches
                if (currentlyMatches) {
                    if (currentStringChar == currentSubstringChar) {
                        if (substrCharsCurrentIndex == substringLength - 1) {
                            return true;
                        } else {
                            substrCharsCurrentIndex++;
                        }
                    } else {
                        currentlyMatches = false;
                        i--;
                        substrCharsCurrentIndex = 0;
                    }
                //proceed with next character when previos did not matches
                } else {
                    if (currentStringChar == currentSubstringChar) {
                        if (substrCharsCurrentIndex == substringLength - 1) {
                            return true;
                        } else {
                            currentlyMatches = true;
                            substrCharsCurrentIndex++;
                        }
                    }
                }
            }
        }

        //handle with any string indicators at the end of string
        for (
            int i = substrCharsCurrentIndex;
            i < substringLength; i++) {
            if (substringChars[i] != ANY_STRING_INDICATOR) {
                return false;
            }
        }
        return true;
    }
}
