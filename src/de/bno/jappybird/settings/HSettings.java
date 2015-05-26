package de.bno.jappybird.settings;

import java.awt.Color;
import java.io.FileInputStream;
import java.util.Properties;

public class HSettings
{

	public static final String KEY_INSTALL_SECURITY_MANAGER = "install_security_manager";
	public static final String KEY_REWARD_SERVER_ADDRESS = "rewad_server_address";
	public static final String KEY_REWARD_MULTIPLIER = "SERVER_ADDRES";
	public static final String KEY_GAME_NAME = "game_name";

	public static final Properties props = new Properties(System.getProperties());

	static
	{

		try
		{
			props.load(new FileInputStream("./settings.properties"));
		}
		catch (Exception e)
		{
			System.err.println(" -> Properties load ex: " + e.getMessage());
		}
	}

	public static String getString(String key, String def)
	{

		String property;
		try
		{
			property = props.getProperty(key, def);
		}
		catch (Exception e)
		{

			return def;
		}

		return property;
	}

	public static boolean getBoolean(String key, boolean def)
	{

		try
		{
			String property = props.getProperty(key).trim();

			if (property == null)
			{

				return def;
			}
			else
			{

				try
				{
					return Boolean.valueOf(property);
				}
				catch (Exception e)
				{
					System.err.println(key + " -> Properties read ex: " + e.getMessage());
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return def;
		}

		return def;
	}

	public static Color getColor(String key, Color def)
	{

		try
		{
			String property = props.getProperty(key).trim();

			if (property == null)
			{

				return def;
			}
			else
			{

				try
				{
					return Color.decode(property);
				}
				catch (Exception e)
				{
					System.err.println(key + " -> Properties read ex: " + e.getMessage());
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return def;
		}
		return def;
	}

	public static Color[] getColorArray(String key, Color[] def)
	{

		try
		{
			String property = props.getProperty(key);

			if (property == null)
			{

				return def;
			}
			else
			{

				try
				{
					String[] colors = property.split(",");
					Color[] cols = new Color[colors.length];

					for (int i = 0; i < cols.length; i++)
					{

						cols[i] = Color.decode(colors[i].trim());
					}

					return cols;
				}
				catch (Exception e)
				{
					System.err.println(key + " -> Properties read ex: " + e.getMessage());
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return def;
		}

		return def;
	}

	public static int getInt(String key, int def)
	{

		try
		{
			String property = props.getProperty(key).trim();

			if (property == null)
			{

				return def;
			}
			else
			{

				try
				{
					return Integer.valueOf(property);
				}
				catch (Exception e)
				{
					System.err.println(key + " -> Properties read ex: " + e.getMessage());
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return def;
		}

		return def;
	}

	public static int[] getIntArray(String key, int[] def)
	{

		try
		{
			String property = props.getProperty(key);

			if (property == null)
			{

				return def;
			}
			else
			{

				try
				{

					String[] ints = property.split(",");
					int[] ret = new int[ints.length];

					int index = 0;
					for (String s : ints)
					{

						ret[index] = Integer.valueOf(s.trim());
						index++;
					}

					return ret;
				}
				catch (Exception e)
				{
					System.err.println(key + " -> Properties read ex: " + e.getMessage());
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return def;
		}

		return def;
	}

	public static double getDouble(String key, double def)
	{

		try
		{
			String property = props.getProperty(key).trim();

			if (property == null)
			{

				return def;
			}
			else
			{

				try
				{
					return Double.valueOf(property);
				}
				catch (Exception e)
				{
					System.err.println(key + " -> Properties read ex: " + e.getMessage());
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return def;
		}

		return def;
	}

	public static Class<?> getClass(String key, Class<?> def)
	{

		Class<?> ret = def;
		try
		{

			try
			{
				Class<?> load = Class.forName(props.getProperty(key));

				if (load != null)
				{

					ret = load;
				}
			}
			catch (ClassNotFoundException e)
			{
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return ret;
	}
}
