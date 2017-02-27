package de.cominto.blaetterkatalog.android.cfl.model;

/**
 * Class TextModifier.
 * TODO: Add Description
 *
 * @author Jan Grünewald (2017)
 * @version 1.0.0
 */
abstract public class TextModifier {
    public static enum Type {
        TITLE_MODIFIER, DESCRIPTION_MODIFIER, CONTENT_MODIFIER
    }

    private final Type modifierType;

    public TextModifier(final Type modifierType) {
        this.modifierType = modifierType;
    }

    abstract String apply(String target);
}
