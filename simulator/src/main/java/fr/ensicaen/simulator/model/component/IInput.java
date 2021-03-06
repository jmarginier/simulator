package fr.ensicaen.simulator.model.component;

import javax.xml.bind.annotation.XmlSeeAlso;

import fr.ensicaen.simulator.model.mediator.Mediator;
import fr.ensicaen.simulator.model.response.IResponse;

@XmlSeeAlso({ ComponentIO.class, ComponentI.class })
public interface IInput {

	/**
	 * Retourne le type de l'élément.
	 * 
	 * @return
	 */
	public int getType();

	/**
	 * Retourne le nom de l'élément.
	 * 
	 * @return
	 */
	public String getName();

	/**
	 * Retourne l'identifiant unique
	 * 
	 * @return
	 */
	public String getUuid();

	/**
	 * Retourne l'acronyme de l'élément.
	 * 
	 * @return
	 */
	public String getAcronym();

	/**
	 * Receipt notification of an input invocation
	 * 
	 * @param m
	 * @param data
	 * @return
	 */
	public IResponse notifyMessage(Mediator m, String data);

}
