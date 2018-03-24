package br.usjt.arqsw.controller;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import br.usjt.arqsw.dao.FilaDAO;
import br.usjt.arqsw.entity.Chamado;
import br.usjt.arqsw.entity.Fila;
import br.usjt.arqsw.service.ChamadoService;
import br.usjt.arqsw.service.FilaService;

/**
 * 
 * @author Leandro Pinheiro de Holanda 816113762
 *
 */
@Controller
public class ManterChamadosController {
	private FilaService filaService;
	private ChamadoService chamadoService;

	@Autowired
	public ManterChamadosController(FilaService fs, ChamadoService cs) {
		filaService = fs;
		chamadoService = cs;
	}

	@RequestMapping("index")
	public String inicio() {
		return "index";
	}

	@RequestMapping("teste")
	public void Teste(Fila fila, Model model) {

		/*FilaDAO filaDAO;
		//filaDAO.criar(fila);
		List<Fila> lista = filaDAO.listarFilas();
		model.setAttribute("lista", lista);*/
		
		/*EntityManagerFactory factory = Persistence.createEntityManagerFactory("servicedesk");
		EntityManager manager = factory.createEntityManager();
		FilaDAO filaDAO = new FilaDAO(manager);
		manager.getTransaction().begin();
		filaDAO.listarFilas();*/
		
	}

	private List<Fila> listarFilas() throws IOException {
		return filaService.listarFilas();
	}

	@RequestMapping("/listar_filas_exibir")
	public String listarFilasExibir(Model model) {
		try {
			model.addAttribute("filas", listarFilas());
			return "ChamadoListar";
		} catch (IOException e) {
			e.printStackTrace();
			return "Erro";
		}
	}

	@RequestMapping("/listar_chamados_exibir")
	public String listarChamadosExibir(@Valid Fila fila, BindingResult result, Model model) throws IOException {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("servicedesk");
		EntityManager manager = factory.createEntityManager();
		FilaDAO filaDAO = new FilaDAO(manager);
		manager.getTransaction().begin();
		filaDAO.carregar(fila.getId());
		model.addAttribute("fila", fila);
		manager.getTransaction().commit();
		
		List<Chamado> chamados = chamadoService.listarChamados(fila);
		model.addAttribute("chamados", chamados);
		
		manager.close();
		factory.close();
		
		return "ChamadoListarExibir";
	}
	
	
	/*public String listarChamadosExibir(@Valid Fila fila, BindingResult result, Model model) {
		try {
			if (result.hasFieldErrors("id")) {
				model.addAttribute("filas", listarFilas());
				System.out.println("Deu erro " + result.toString());
				return "ChamadoListar";
				// return "redirect:listar_filas_exibir";
			}
			fila = filaService.carregar(fila.getId());
			model.addAttribute("fila", fila);

			// TODO Código para carregar os chamados
			List<Chamado> chamados = chamadoService.listarChamados(fila);
			model.addAttribute("chamados", chamados);

			return "ChamadoListarExibir";

		} catch (IOException e) {
			e.printStackTrace();
			return "Erro";
		}
	}*/

}
