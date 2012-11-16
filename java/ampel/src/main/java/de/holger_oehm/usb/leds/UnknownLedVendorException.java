package de.holger_oehm.usb.leds;

import de.holger_oehm.usb.hid.USBAddress;

public class UnknownLedVendorException extends RuntimeException {

    public UnknownLedVendorException(final USBAddress address) {
        super("Unknown vendor for device " + address);
    }

}
