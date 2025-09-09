package logica;

import modelo.Historias;

public class ManejadorBD {

	
	
	public int  persistirMensajesNuevos(Historias nuevos) {
		int persistidos=0;
		//borrar mensajes persistidos
		if (!nuevos.getMensajesEnviados().isEmpty()){
			
		}
		return persistidos;
	}
}
