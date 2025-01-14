package dev.aisandbox.client.output;

import org.junit.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.Assert.assertEquals;

public class OutputToolsTest {

    @Test
    public void testConstants() {
        assertEquals("Video width", 1920, OutputTools.VIDEO_WIDTH);
        assertEquals("Video height", 1080, OutputTools.VIDEO_HEIGHT);
    }

    @Test
    public void testCreateBlank() {
        BufferedImage image = OutputTools.getBlankScreen();
        assertEquals("Image width", 1920, image.getWidth());
        assertEquals("Image height", 1080, image.getHeight());
    }


    @Test
    public void testCreateWhiteScreen() {
        BufferedImage image = OutputTools.getWhiteScreen();
        assertEquals("Image width", 1920, image.getWidth());
        assertEquals("Image height", 1080, image.getHeight());
        assertEquals("White background", Color.WHITE, new Color(image.getRGB(0, 0)));
    }


    @Test
    public void testBlackScreen() {
        BufferedImage image = OutputTools.getBlackScreen();
        assertEquals("Image width", 1920, image.getWidth());
        assertEquals("Image height", 1080, image.getHeight());
        assertEquals("Black background", Color.BLACK, new Color(image.getRGB(0, 0)));
    }
}
