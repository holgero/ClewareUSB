package de.holger_oehm.usb.leds;

import de.holger_oehm.usb.hid.HiDevice;

public class ClewareAmpel extends AbstractLeds implements USBLeds {

    private static final int RED_LED = 0x010;
    private static final int YELLOW_LED = 0x011;
    private static final int GREEN_LED = 0x012;

    public ClewareAmpel(final HiDevice device) {
        super(device, 3);
    }

    @Override
    public void red() {
        setState(1, 0, 0);
    }

    private void setState(final int red, final int yellow, final int green) {
        setReportData(0, RED_LED, red);
        setReportData(0, YELLOW_LED, yellow);
        setReportData(0, GREEN_LED, green);
    }

    @Override
    public void off() {
        setState(0, 0, 0);
    }

    @Override
    public void yellow() {
        setState(0, 1, 0);
    }

    @Override
    public void green() {
        setState(0, 0, 1);
    }
}
