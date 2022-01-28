package at.ac.univie.unet.a01638800.raytracer.writer;

public class ImageWriterException extends Throwable {

    public ImageWriterException() {
    }

    public ImageWriterException(final String message) {
        super(message);
    }

    public ImageWriterException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ImageWriterException(final Throwable cause) {
        super(cause);
    }

    public ImageWriterException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
