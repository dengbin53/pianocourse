
/**
 * SeeScore For Android
 * Dolphin Computing http://www.dolphin-com.co.uk
 */
/* SeeScoreLib Key for YinYue (319)

 IMPORTANT! This file is for YinYue only.
 It must be used only for the application for which it is licensed,
 and must not be released to any other individual or company.

 Please keep it safe, and make sure you don't post it online or email it.
 Keep it in a separate folder from your source code, so that when you backup the code
 or store it in a source management system, the key is not included.
 */

package uk.co.dolphin_com.seescoreandroid;

import uk.co.dolphin_com.sscore.SScoreKey;

/**
 * The licence key to enable features in SeeScoreLib supplied by Dolphin Computing
 */
public class LicenceKeyInstance {
    // licence keys: draw, contents, play_data, item_colour, android, embed_id
    private static final int[] keycap = {0X840a5, 0X0};
    private static final int[] keycode = {0X41813b3d, 0Xb584364, 0X7109f4d3, 0X4fb0600d, 0X44721e5a, 0X9dc9479d, 0X2026c9d1, 0X528bc8d4, 0X3dba67b7, 0X572e00a2, 0Xd2bb865, 0X3f0c736e, 0X3156f5a4, 0X4418fb4a, 0X6df9fd01};

    public static final SScoreKey SeeScoreLibKey = new SScoreKey("YinYue", keycap, keycode);
}
