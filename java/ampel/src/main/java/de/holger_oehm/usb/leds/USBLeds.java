/*
 *  Copyright (C) 2012 Holger Oehm
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.holger_oehm.usb.leds;

import java.io.Closeable;
import java.util.Iterator;

import de.holger_oehm.usb.hid.HiDevice;
import de.holger_oehm.usb.hid.HiDeviceException.HIDDeviceNotFoundException;
import de.holger_oehm.usb.hid.HiDeviceFactory;
import de.holger_oehm.usb.hid.USBAddress;

public interface USBLeds extends Closeable {
    public static final class Factory {
        private static final USBAddress DREAM_CHEEKY = new USBAddress(0x1d34, 0x0004);
        private static final USBAddress CLEWARE = new USBAddress(0x0d50, 0x0008);

        private static final class LedDevicesIterator implements Iterator<USBLeds> {
            boolean triedDreamCheeky = false;
            boolean triedCleware = false;
            USBLeds next = null;

            @Override
            public boolean hasNext() {
                if (next != null) {
                    return true;
                }
                if (!triedDreamCheeky) {
                    triedDreamCheeky = true;
                    tryCreateNext(DREAM_CHEEKY);
                }
                if (next != null) {
                    return true;
                }
                if (!triedCleware) {
                    triedCleware = true;
                    tryCreateNext(CLEWARE);
                }
                if (next != null) {
                    return true;
                }
                return false;
            }

            private void tryCreateNext(final USBAddress address) {
                try {
                    next = createInstance(address);
                } catch (final HIDDeviceNotFoundException e) {
                }
            }

            @Override
            public USBLeds next() {
                if (!hasNext()) {
                    return null;
                }
                final USBLeds result = next;
                next = null;
                return result;
            }

            @Override
            public void remove() {
            }
        }

        public static Iterator<USBLeds> enumerateLedDevices() {
            return new LedDevicesIterator();
        }

        public static USBLeds createInstance(final USBAddress address) {
            final HiDevice device = new HiDeviceFactory().create(address);
            if (address.getVendorId() == DREAM_CHEEKY.getVendorId()) {
                return new DreamCheekyLeds(device);
            }
            if (address.getVendorId() == CLEWARE.getVendorId()) {
                return new ClewareAmpel(device);
            }
            throw new UnknownLedVendorException(address);
        }
    }

    void red();

    void off();

    void yellow();

    void green();

    void blue();

    void white();

    void magenta();

    void cyan();

    /**
     * Switches the LEDs off, closes this USBLeds device and releases any system
     * resources associated with it.
     */
    @Override
    public void close();
}
