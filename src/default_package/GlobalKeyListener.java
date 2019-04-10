package default_package;

import me.coley.simplejna.hook.key.KeyEventReceiver;
import me.coley.simplejna.hook.key.KeyHookManager;

public class GlobalKeyListener extends KeyEventReceiver
{
	private int ctrlPressed;
	private int altPressed;

	public GlobalKeyListener(KeyHookManager hookManager)
	{
		super(hookManager);
		ctrlPressed = 0;
		altPressed = 0;
	}

	public boolean onKeyUpdate(SystemState sysState, PressState pressState, int time, int vkCode)
	{
		if (pressState == PressState.UP)
		{
			System.out.print("Released ");
			if (vkCode == 162 || vkCode == 163)
			{
				ctrlPressed--;
			}
			if (vkCode == 164 || vkCode == 165)
			{
				altPressed--;
			}
		} else if (pressState == PressState.DOWN)
		{
			System.out.print("Pressed ");
			if (vkCode == 162 || vkCode == 163)
			{
				ctrlPressed++;
			}
			if (vkCode == 164 || vkCode == 165)
			{
				altPressed++;
			}
			if (vkCode == 46 && ctrlPressed > 0 && altPressed > 0)
			{
				// TODO Be careful ;)
				System.err.println("Don't do that, you fucking shit!!!");
			}
		} else
		{
			System.out.print("Unknown ");
		}

		if (vkCode == 67)
		{
			if (ctrlPressed > 0)
			{
				System.out.println("CTRL + C");
			}
			return false;
		} else if (vkCode == 86)
		{
			if (ctrlPressed > 0)
			{
				System.out.println("CTRL + V");
			}
			return false;
		} else if (vkCode >= 112 && vkCode <= 123)
		{
			System.out.println("functional button");
			return true;
		} else if (vkCode == 162)
		{
			System.out.println("l ctrl");
			return true;
		} else if (vkCode == 91)
		{
			System.out.println("l win");
			return true;
		} else if (vkCode == 164)
		{
			System.out.println("l alt");
			return true;
		} else if (vkCode == 165)
		{
			System.out.println("r alt");
			return true;
		} else if (vkCode == 93)
		{
			System.out.println("r menu");
			return true;
		} else if (vkCode == 163)
		{
			System.out.println("r ctrl");
			return true;
		} else
		{
			System.out.println(vkCode);
			return false;
		}
	}
}