package in.ashar.handleFiles.utility;

import java.awt.*;
import java.awt.image.*;
import java.awt.geom.AffineTransform;
import java.io.*;
import javax.imageio.ImageIO;

public class ImagePreprocessor {

    /**
     * Main preprocessing pipeline optimized for cheque images.
     * Call this before passing the image to Tesseract.
     */
    public static BufferedImage preprocessForCheque(BufferedImage src) throws IOException {
        // 1) convert to grayscale
        BufferedImage gray = toGray(src);

        // 2) optional resize: if image is small, upscale to improve OCR
        BufferedImage resized = resizeIfNeeded(gray, 1500);


        // 4) denoise (median filter)
        BufferedImage denoised = medianFilter(resized, 3);

        // 5) binarize using Otsu's threshold
        BufferedImage binary = otsuBinarize(denoised);
        ImageIO.write(binary,"jpg",new File("binary.jpg"));
        // Return binary/morphed image which is usually best for Tesseract on cheques
        return binary;
    }

    // -----------------------
    // Helper functions
    // -----------------------

    /** Convert image to TYPE_BYTE_GRAY */
    private static BufferedImage toGray(BufferedImage src) {
        if (src.getType() == BufferedImage.TYPE_BYTE_GRAY) return src;
        BufferedImage gray = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D g = gray.createGraphics();
        g.drawImage(src, 0, 0, null);
        g.dispose();
        return gray;
    }

    /** Upscale image if width is less than minWidth (keeps aspect ratio) */
    private static BufferedImage resizeIfNeeded(BufferedImage src, int minWidth) {
        int w = src.getWidth();
        int h = src.getHeight();
        if (w >= minWidth) return src;
        double scale = (double)minWidth / w;
        int nw = (int)Math.round(w * scale);
        int nh = (int)Math.round(h * scale);
        BufferedImage dst = new BufferedImage(nw, nh, src.getType());
        Graphics2D g = dst.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        AffineTransform at = AffineTransform.getScaleInstance(scale, scale);
        g.drawRenderedImage(src, at);
        g.dispose();
        return dst;
    }


    /** 3x3 median filter (neighborhoodSize should be odd, e.g., 3) */
    private static BufferedImage medianFilter(BufferedImage src, int neighborhoodSize) {
        if (neighborhoodSize % 2 == 0) neighborhoodSize++;
        int radius = neighborhoodSize / 2;
        int w = src.getWidth(), h = src.getHeight();
        BufferedImage dst = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_GRAY);
        Raster in = src.getRaster();
        WritableRaster out = dst.getRaster();
        int maxN = neighborhoodSize * neighborhoodSize;
        int[] window = new int[maxN];

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int n = 0;
                for (int j = -radius; j <= radius; j++) {
                    int yy = y + j;
                    if (yy < 0 || yy >= h) continue;
                    for (int i = -radius; i <= radius; i++) {
                        int xx = x + i;
                        if (xx < 0 || xx >= w) continue;
                        window[n++] = in.getSample(xx, yy, 0);
                    }
                }
                // sort window[0..n-1] to get median
                java.util.Arrays.sort(window, 0, n);
                int median = window[n/2];
                out.setSample(x, y, 0, median);
            }
        }
        dst.setData(out);
        return dst;
    }

    /** Otsu's method for binarization */
    private static BufferedImage otsuBinarize(BufferedImage src) {
        int w = src.getWidth(), h = src.getHeight();
        Raster r = src.getRaster();
        int[] hist = new int[256];

        for (int y = 0; y < h; y++)
            for (int x = 0; x < w; x++)
                hist[r.getSample(x, y, 0)]++;

        int total = w * h;
        float sum = 0;
        for (int t = 0; t < 256; t++) sum += t * hist[t];

        float sumB = 0;
        int wB = 0;
        int wF = 0;
        float varMax = 0;
        int threshold = 0;

        for (int t = 0; t < 256; t++) {
            wB += hist[t];
            if (wB == 0) continue;
            wF = total - wB;
            if (wF == 0) break;
            sumB += (float) (t * hist[t]);
            float mB = sumB / wB;
            float mF = (sum - sumB) / wF;
            // Between class variance
            float varBetween = (float) wB * (float) wF * (mB - mF) * (mB - mF);
            if (varBetween > varMax) {
                varMax = varBetween;
                threshold = t;
            }
        }

        BufferedImage dst = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_BINARY);
        WritableRaster wr = dst.getRaster();
        for (int y = 0; y < h; y++)
            for (int x = 0; x < w; x++) {
                int val = r.getSample(x, y, 0);
                wr.setSample(x, y, 0, (val > threshold) ? 1 : 0); // binary raster: 0 or 1
            }
        dst.setData(wr);
        return dst;
    }

}
