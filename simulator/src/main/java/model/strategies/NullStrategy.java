package model.strategies;

import model.component.Component;
import model.component.IOutput;
import model.mediator.Mediator;
import model.response.IResponse;
import model.response.VoidResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import simulator.Context;

public class NullStrategy implements IStrategy<Component> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8566363354337168824L;
	private static Logger log = LoggerFactory.getLogger(NullStrategy.class);
	private String test = "toto";

	@Override
	public void init(IOutput _this, Context ctx) {
	}

	@Override
	public IResponse processMessage(Component component, Mediator mediator, String data) {
		log.info("Input treatment with data = " + data);
		return VoidResponse.build();
	}

	@Override
	public void processEvent(Component _this, String event) {
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((test == null) ? 0 : test.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NullStrategy other = (NullStrategy) obj;
		if (test == null) {
			if (other.test != null)
				return false;
		}
		else if (!test.equals(other.test))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Null strategy";
	}
}
