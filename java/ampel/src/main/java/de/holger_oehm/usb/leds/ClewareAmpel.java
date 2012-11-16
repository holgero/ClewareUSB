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

    @Override
    public void set(final LedColor... colors) {
        int red = 0, yellow = 0, green = 0;
        for (final LedColor ledColor : colors) {
            switch (ledColor) {
            case RED:
                red = 1;
                break;
            case YELLOW:
                yellow = 1;
                break;
            case GREEN:
                green = 1;
                break;
            default:
                throw new IllegalStateException("Unexpected color " + ledColor);
            }
        }
        setState(red, yellow, green);
    }
}
