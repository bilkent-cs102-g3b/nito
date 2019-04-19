package admin;

import admin.model.Model;

/*
 * TODO Create different view interfaces for different data, e.g. examineeView,
 * examView, settingsView...
 */

/**
 * An interface for admin views
 * @author Ziya Mukhtarov
 * @version 19/04/2019
 */
public interface NitoAdminView
{
	/**
	 * Updates the view according to the Model
	 * @param m The Nito admin model
	 */
	public void updateView( Model m);
}
