package fr.ensicaen.simulator.model.response;


public class VoidResponse implements IResponse {

	public static VoidResponse build() {
		return new VoidResponse();
	}

	@Override
	public boolean isVoid() {
		return true;
	}
}
