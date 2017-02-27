package de.cominto.blaetterkatalog.android.cfl.model;

/**
 * Class ImageTagRemovalModifier.
 * Remove html-image tags from the text
 *
 * @author Jan Grünewald (2017)
 * @version 1.0.0
 */
public class ImageTagRemovalModifier extends TextModifier {

    private static final String htmlImagePattern = "<img.*?/?>";

    public ImageTagRemovalModifier(Type modifierType) {
        super(modifierType);

    }

    @Override
    public String apply(String target) {
        return target.replaceAll(htmlImagePattern, "");
    }
}
