/**
 *
 */
package de.hybris.platform.addons.wsclientgenerator.exceptions;

/**
 * @author Ahmed-LAJMI
 *
 */
public class InvokeWsException extends Exception
{
	String code;

	public InvokeWsException()
	{
		super();
	}

	public InvokeWsException(final String message)
	{
		super(message);
	}

	public InvokeWsException(final String message, final String code)
	{
		super(message);
		this.code = code;
	}
}
