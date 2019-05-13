package examinee.model;

import me.coley.simplejna.hook.key.KeyEventReceiver;
import me.coley.simplejna.hook.key.KeyHookManager;

public class KeyListener extends KeyEventReceiver
{
	public static final int[] blockedCodes = { 91, 93, 112, 113, 114, 115, 116, 117, 118, 119,
			 120, 121, 123, 162, 163, 164, 165, 27};
	
	public KeyListener(KeyHookManager hookManager)
	{
		super(hookManager);
	}
	
	private boolean codeInArray( int num)
	{
		boolean result = false;
		
		for( int i = 0; i < blockedCodes.length; i++)
		{
			if ( blockedCodes[i] == num )
				result = true;
		}
		
		return result;
	}

	public boolean onKeyUpdate( SystemState sysState, PressState pressState, int time, int vkCode)
	{
		boolean result = false;
		
		if (pressState == PressState.DOWN)
		{
			if ( codeInArray( vkCode))
			{
				result = true;
				System.out.print("Pressed ");
			}
		}
		
		return result;
	}
}
