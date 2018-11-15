package models;

public enum ServiceProviderStatus {
    PENDENT("pendent"), APPROVED("approved"), REJECTED("rejected");


    final String txt;

    ServiceProviderStatus(final String txt1) {

        this.txt = txt1;
    }

    @Override
    public String toString() {
        return txt;
    }
}
