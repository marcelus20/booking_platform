package models;

public enum BookingStatus {

    CONFIRMED("confirmed"),
    PENDENT("pendent"),
    COMPLETE("complete"),
    USER_DID_NOT_ARRIVE("did not arrive");

    private final String text;

    BookingStatus(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
