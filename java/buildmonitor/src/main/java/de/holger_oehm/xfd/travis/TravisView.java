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

package de.holger_oehm.xfd.travis;

import java.util.Date;

import de.holger_oehm.xfd.BuildState;

public class TravisView {

    public static class TravisJob {
        String slug;
        int last_build_status;
        Date last_build_started_at;
        Date last_build_finished_at;
        
        BuildState getState() {
            if (last_build_started_at  != null &&
                last_build_finished_at != null ) {
                if ( last_build_status == 0 ) {
                    return BuildState.OK;
                } else {
                    return BuildState.FAILED;
                }
            } else if (last_build_started_at  != null &&
                       last_build_finished_at == null ) {
                if ( last_build_status == 0 ) {
                    return BuildState.OK_BUILDING;
                } else {
                    return BuildState.FAILED_BUILDING;
                }
            } else {
                return BuildState.INSTABLE_BUILDING;
                // return BuildState.INSTABLE; 
            }
        }
        
        @Override
        public String toString() {
            return "TravisView [slug=\"" + slug + 
                    "\", last_build_status=" + last_build_status + 
                    ", last_build_started_at=|" + last_build_started_at + 
                    "|, last_build_finished_at=|" + last_build_finished_at + 
          "|]";
        }
    }
    
    public BuildState getState() {
        throw new UnsupportedOperationException("not yet...");
    }

    @Override
    public String toString() {
        return "TravisView []";
    }
}
