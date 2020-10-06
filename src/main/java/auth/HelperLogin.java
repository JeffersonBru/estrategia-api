package auth;

import org.json.JSONObject;
import model.Usuario;
import suporte.Suporte;

public class HelperLogin extends Suporte {
	
	public Usuario gerarEmpregadoRandom() {
		String nomeUsuario = "usuario_".concat(obterData("ddMMyyyyHHmmssSSS"));
		return new Usuario(nomeUsuario, nomeUsuario.concat("@teste.com"), "123456");
	}

	public JSONObject gerarBody(Usuario usuario) {
		JSONObject usuarioJson = new JSONObject();
		if (usuario.name != null)
			usuarioJson.put("name", usuario.name);
		usuarioJson.put("email", usuario.email);
		usuarioJson.put("password", usuario.senha);
		return usuarioJson;
	}

	public JSONObject gerarUsuarioRandomBody() {
		return gerarBody(gerarEmpregadoRandom());
	}

}
