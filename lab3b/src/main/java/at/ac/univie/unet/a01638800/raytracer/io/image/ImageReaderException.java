package at.ac.univie.unet.a01638800.raytracer.io.image;

public class ImageReaderException extends Exception {
    public ImageReaderException() {
    }

    public ImageReaderException(final String message) {
        super(message);
    }

    public ImageReaderException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ImageReaderException(final Throwable cause) {
        super(cause);
    }

    public ImageReaderException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
