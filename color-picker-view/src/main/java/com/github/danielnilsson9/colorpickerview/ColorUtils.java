/*
 * Copyright (C) 2015 Daniel Nilsson
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.danielnilsson9.colorpickerview;

import android.graphics.Color;

import java.util.regex.Pattern;

public class ColorUtils {
    /** Format for #AARRGGBB and #RRGGBB. **/
    private final static Pattern HEXADECIMAL_FORMAT = Pattern.compile("^#([A-Fa-f0-9]{8}|[A-Fa-f0-9]{6})$");
    /** Format for #RGB **/
    private final static Pattern HEXADECIMAL_SHORT_FORMAT = Pattern.compile("^#([A-Fa-f0-9]{3})$");

    /**
     * Check if the provided color code is valid according to the following hexadecimal color formats:
     *      #AARRGGBB, #RRGGBB or #RGB.
     * @param colorCode color code to check
     * @return {@code true} color code is valid according to the formats
     */
    public static boolean isValidHexadecimal(String colorCode) {
        return HEXADECIMAL_FORMAT.matcher(colorCode).matches() ||
                HEXADECIMAL_SHORT_FORMAT.matcher(colorCode).matches();
    }

    /**
     * Parse the color code, and return the corresponding color-int.
     * If the string cannot be parsed, throws an IllegalArgumentException
     * exception. Supported formats are:
     * #RRGGBB
     * #AARRGGBB
     * #RGB
     * @param colorCode color code to parse
     * @return color-int
     */
    public static int parseColor(String colorCode) {
        if (HEXADECIMAL_SHORT_FORMAT.matcher(colorCode).matches()) {
            colorCode = String.format("#%c%c%c%c%c%c", colorCode.charAt(1), colorCode.charAt(1),
                    colorCode.charAt(2), colorCode.charAt(2), colorCode.charAt(3), colorCode.charAt(3));
        }
        return Color.parseColor(colorCode);
    }

    /**
     * Convert a color-int into a color hexadecimal string.
     * Format will be #AARRGGBB if includeAlpha is true, or #RRGGBB if is false.
     *
     * @param color color int
     * @param includeAlpha if true hexadecimal will include alpha channel
     * @return color code for the color-int specified and based on the includeAlpha
     */
    public static String colorToString(int color, boolean includeAlpha) {
        if (includeAlpha) {
            return "#" + Integer.toHexString(color);
        } else {
            return "#" + Integer.toHexString(color).substring(2);
        }
    }
}
