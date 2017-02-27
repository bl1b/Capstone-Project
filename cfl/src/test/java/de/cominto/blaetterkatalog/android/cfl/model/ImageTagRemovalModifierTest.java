package de.cominto.blaetterkatalog.android.cfl.model;


import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Class ImageTagRemovalModifierTest.
 * TODO: Add Description
 *
 * @author Jan Grünewald (2017)
 * @version 1.0.0
 */
public class ImageTagRemovalModifierTest {

    private static final String closedAtStart = "<img src=\"http://www.google.de/img1.jpg\"/>Some test.";
    private static final String openAtStart = "<img src=\"http://www.google.de/img1.jpg\"> Some test.";
    private static final String multiple = "<img src=\"http://www.google.de/img1.jpg\"/> Some test. <img src=\"http://www.google.de/img1.jpg\">  Yet another one.";
    private static final String none = "Some test.";

    private ImageTagRemovalModifier imageTagRemovalModifier;

    @Before
    public void setupModifier() {
        imageTagRemovalModifier = new ImageTagRemovalModifier(TextModifier.Type.CONTENT_MODIFIER);
    }

    @Test
    public void testClosedAtStart() throws Exception {
        String result = imageTagRemovalModifier.apply(closedAtStart);
        assertThat(result, equalTo("Some test."));
    }

    @Test
    public void testOpenAtStart() throws Exception {
        String result = imageTagRemovalModifier.apply(openAtStart);
        assertThat(result, equalTo(" Some test."));
    }

    @Test
    public void testMultiple() throws Exception {
        String result = imageTagRemovalModifier.apply(multiple);
        assertThat(result, equalTo(" Some test.   Yet another one."));
    }

    @Test
    public void testNone() throws Exception {
        String result = imageTagRemovalModifier.apply(none);
        assertThat(result, equalTo(none));
    }
}
