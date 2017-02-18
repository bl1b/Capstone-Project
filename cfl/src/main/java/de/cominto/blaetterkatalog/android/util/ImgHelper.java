package de.cominto.blaetterkatalog.android.util;

import android.util.Base64;

import java.io.File;
import java.io.IOException;

import timber.log.Timber;

/**
 * Class ImgHelper.
 * TODO: Add Description
 *
 * @author Jan Grünewald (2017)
 * @version 1.0.0
 */
public class ImgHelper {
    public static String createImageFilename(String fileIdentifier) {
        return createImageFilename(fileIdentifier, null);
    }

    public static String createImageFilename(String fileIdentifier, String suffix) {
        String filename = Base64.encodeToString(fileIdentifier.getBytes(), Base64.DEFAULT);
        filename += (suffix != null && !suffix.isEmpty()) ? "_" + suffix + ".png" : ".png";
        return filename;
    }

    public static File createImageFile(File imageDir, String fileIdentifier) throws IOException {
        return createImageFile(imageDir, fileIdentifier, null);
    }

    public static File createImageFile(File imageDir, String fileIdentifier, String suffix) throws IOException {
        if (imageDir == null) {
            throw new IOException("Image-Directory is null.");
        }

        File imageFile = new File(imageDir, ImgHelper.createImageFilename(fileIdentifier, suffix));
        if (!imageFile.isFile() && !imageFile.createNewFile()) {
            Timber.e("Image-File for overview-image could not be created.");
            throw new IOException("Image-File could not be created.");
        }

        return imageFile;

    }
}
