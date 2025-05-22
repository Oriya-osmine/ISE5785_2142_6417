package renderer;


import org.junit.jupiter.api.Test;
import primitives.Color;


/**
 * Tests for {@link renderer.ImageWriter}
 */
public class ImageWriterTest {

    /**
     * Test method for {@link renderer.ImageWriter} building and writing an image.
     */
    @Test
    void testImageWriter() {
        // TC01: create an image
        ImageWriter imageWriter = new ImageWriter(800, 500);

        Color backgroundColor = new Color(15, 155, 78);//black
        Color gridColor = new Color(255, 255, 255);//white

        int gridRows = 10;
        int gridCols = 16;

        int cellWidth = imageWriter.nX() / gridCols;
        int cellHeight = imageWriter.nY() / gridRows;

        for (int x = 0; x < imageWriter.nX(); x++) {
            for (int y = 0; y < imageWriter.nY(); y++) {
                imageWriter.writePixel(x, y, backgroundColor);
            }
        }

        for (int row = 0; row < gridRows; row++) {
            int y = row * cellHeight;
            for (int x = 0; x < imageWriter.nX(); x++) {
                imageWriter.writePixel(x, y, gridColor);
            }
        }

        for (int col = 0; col < gridCols; col++) {
            int x = col * cellWidth;
            for (int y = 0; y < imageWriter.nY(); y++) {
                imageWriter.writePixel(x, y, gridColor);
            }
        }

        imageWriter.writeToImage("image");
    }
}





