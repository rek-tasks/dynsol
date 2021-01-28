package pl.tomek.utils;

public class SubstringUtils {

    private static final char ANY_STRING_INDICATOR = '*';
    private static final char ANY_STRING_ESCAPE_INDICATOR = '\\';

    private SubstringUtils() {
    }

    public static boolean isSubstring(String string, String substring) {

        //check obvious boundary conditions
        if (string == null || substring == null) {
            return false;
        }

        if ("".equals(substring) || "*".equals(substring)) {
            return true;
        }

        //init vars
        char[] stringChars = string.toCharArray();
        char[] substringChars = substring.toCharArray();

        int stringLength = stringChars.length;
        int substringLength = substringChars.length;

        int substrCharsCurrentIndex = 0;

        int lastSubStringCorrectMatchingIndex = -1;

        boolean currentlyMatches = false;
        boolean isAnyCharMode = false;

        for (int i = 0; i < stringLength; i++) {
            char currentStringChar = stringChars[i];
            char currentSubstringChar = substringChars[substrCharsCurrentIndex];

            //handle with escape indicator
            if (currentSubstringChar == ANY_STRING_ESCAPE_INDICATOR && substrCharsCurrentIndex < substringLength - 1
                && substringChars[substrCharsCurrentIndex + 1] == ANY_STRING_INDICATOR) {
                substrCharsCurrentIndex++;
                currentSubstringChar = substringChars[substrCharsCurrentIndex];
            }

            //handle with any string indicator
            if (currentSubstringChar == ANY_STRING_INDICATOR && (substrCharsCurrentIndex == 0
                || substringChars[substrCharsCurrentIndex - 1] != ANY_STRING_ESCAPE_INDICATOR)) {
                for (int j = substrCharsCurrentIndex + 1; j < substringLength; j++) {
                    if (j == substringLength - 1) {
                        return substringChars[j] == currentStringChar || substringChars[j] == ANY_STRING_INDICATOR;
                    }

                    if (substringChars[j] != ANY_STRING_INDICATOR) {
                        substrCharsCurrentIndex = j;
                        //check if next chars are escape indicator + any string indicator
                        if (substringChars[substrCharsCurrentIndex] == ANY_STRING_ESCAPE_INDICATOR &&
                            substrCharsCurrentIndex + 1 < substringLength &&
                            substringChars[substrCharsCurrentIndex + 1] == ANY_STRING_INDICATOR) {
                            substrCharsCurrentIndex++;
                        }
                        currentSubstringChar = substringChars[substrCharsCurrentIndex];
                        isAnyCharMode = true;
                        break;
                    }
                }
            }

            //handle process in any char mode (escape asterisk detected)
            if (isAnyCharMode) {
                if (currentStringChar == currentSubstringChar) {
                    lastSubStringCorrectMatchingIndex = substrCharsCurrentIndex;
                    substrCharsCurrentIndex++;
                    currentlyMatches = true;
                    isAnyCharMode = false;
                }
                //handle with normal matching mode
            } else {
                //proceed with next character when previous matches
                if (currentlyMatches) {
                    if (currentStringChar == currentSubstringChar) {
                        substrCharsCurrentIndex++;
                    } else {
                        //if chars do not matches, rewind substring to last matching position
                        if (lastSubStringCorrectMatchingIndex > 0) {
                            substrCharsCurrentIndex = lastSubStringCorrectMatchingIndex;
                            currentlyMatches = true;
                        } else {
                            currentlyMatches = false;
                            i--;
                            substrCharsCurrentIndex = 0;
                        }
                    }
                    //proceed with next character when previos did not matches
                } else {
                    if (currentStringChar == currentSubstringChar) {
                        currentlyMatches = true;
                        substrCharsCurrentIndex++;
                    }
                }
            }

            //end algorithm when substring was iterated entirely
            if (substrCharsCurrentIndex == substringLength - 1) {
                return currentlyMatches;
            }
        }

        //handle with any string indicators at the end of string
        for (int i = substrCharsCurrentIndex; i < substringLength; i++) {
            if (substringChars[i] != ANY_STRING_INDICATOR) {
                return false;
            }
        }
        return currentlyMatches;
    }
}
