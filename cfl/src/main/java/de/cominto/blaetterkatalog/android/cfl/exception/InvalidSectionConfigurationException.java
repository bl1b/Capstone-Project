/*******************************************************************************
 * Copyright (c) 2017 Jan Grünewald.
 * All rights reserved.
 * This project is licensed under NPOSL-3.0 (https://opensource.org/licenses/NPOSL-3.0).
 ******************************************************************************/

package de.cominto.blaetterkatalog.android.cfl.exception;

/**
 * Class InvalidSectionConfigurationException.
 * This exception will be thrown when the configuration for a Section is
 * not correct.
 *
 * @author Jan Grünewald (2017)
 * @version 1.0.0
 */
public class InvalidSectionConfigurationException extends Exception {
    public InvalidSectionConfigurationException(String message, Throwable ex) {
        super(message, ex);
    }

    public InvalidSectionConfigurationException(String message) {
        super(message);
    }
}
