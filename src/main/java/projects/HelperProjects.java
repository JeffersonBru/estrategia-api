package projects;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import model.Projeto;
import model.Tasks;
import suporte.GeradorDeTexto;
import suporte.Suporte;

public class HelperProjects extends Suporte {
	
	public Projeto gerarProject(String idUsuario) {
		String nameProject = "Project_".concat(obterData("ddMMyyyyHHmmssSSS"));
		return new Projeto(nameProject, new GeradorDeTexto().textoAleatorio(), gerarTask(idUsuario));
	}
	
	public List<Tasks> gerarTask(String idUsuario) {
		String nameTask = "Task_".concat(obterData("ddMMyyyyHHmmssSSS"));
		List<Tasks> tasks = new ArrayList<Tasks>();
		tasks.add(new Tasks(nameTask, idUsuario));
		return tasks;
	}

	public JSONObject gerarBody(Projeto project) {
		JSONObject projetoJson = new JSONObject();
		JSONObject tasksJson = new JSONObject();
		JSONArray tasksArray = new JSONArray();
		projetoJson.put("title", project.title);
		projetoJson.put("description", project.description);
		
		 for (Tasks task : project.tasks) {
			 tasksJson.put("name", task.name);
			 tasksJson.put("assignedTo", task.assignedTo);
		}
		 
		 tasksArray.put(tasksJson);
		 
		projetoJson.put("tasks", tasksArray);
		return projetoJson;
	}

}
