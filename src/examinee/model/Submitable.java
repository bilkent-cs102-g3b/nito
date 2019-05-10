package examinee.model;

import common.network.Client;

/**
 * Submittable class for sending parts to admin
 * @author Alper Sari
 * @version 09/05/2019
 */
public interface Submitable
{
	public void submit( Client c);
}
