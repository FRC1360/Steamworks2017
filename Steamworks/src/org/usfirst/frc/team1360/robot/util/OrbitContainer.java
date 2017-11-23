/*
 * Copyright 2017 Oakville Community FIRST Robotics
 * 
 * This file is part of LibOrbit.
 * 
 * LibOrbit is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * LibOrbit is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with LibOrbit.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributions:
 * 
 * Nicholas Mertin (2017-07-04) - set up team project
 */

package org.usfirst.frc.team1360.robot.util;

/**
 * @param <T> The object type
 */
public final class OrbitContainer<T> {
    private T value;

    /**
     * @param value The initial value
     */
    public OrbitContainer(T value) {
        this.value = value;
    }

    /**
     * @return The current value
     */
    public T getValue() {
        return value;
    }

    /**
     * @param value The new value
     */
    public void setValue(T value) {
        this.value = value;
    }
}
