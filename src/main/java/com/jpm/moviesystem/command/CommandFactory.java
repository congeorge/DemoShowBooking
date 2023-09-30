package com.jpm.moviesystem.command;


import com.jpm.moviesystem.exceptions.IncorrectParametersException;

/**
 * Factory for creating Command objects.
 */
public interface CommandFactory {
	
	public Command buildCommand(String[] inputArgs) throws IncorrectParametersException;
	
}
