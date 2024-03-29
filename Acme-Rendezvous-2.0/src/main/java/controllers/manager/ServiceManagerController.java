
package controllers.manager;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CategoryService;
import services.ManagerService;
import services.ServiceService;
import controllers.AbstractController;
import domain.Category;
import domain.Manager;
import domain.Service;
import forms.ServiceForm;

@Controller
@RequestMapping("/service/manager")
public class ServiceManagerController extends AbstractController {

	// Services ---------------
	@Autowired
	private ServiceService	serviceService;

	@Autowired
	private ManagerService	managerService;

	@Autowired
	private CategoryService	categoryService;


	// Constructors -----------
	public ServiceManagerController() {
		super();
	}

	// Listing ----------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		Assert.isTrue(this.managerService.checkAuthority());

		ModelAndView res;
		Collection<Service> services;

		final Manager manager = this.managerService.findByPrincipal();
		services = this.serviceService.findByManagerId(manager.getId());

		res = new ModelAndView("service/list");
		res.addObject("services", services);
		res.addObject("requestURI", "service/manager/list.do");

		return res;
	}

	// Create --------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		Assert.isTrue(this.managerService.checkAuthority());

		ModelAndView res;
		Service service;
		ServiceForm serviceForm;

		service = this.serviceService.create();
		serviceForm = this.serviceService.construct(service);

		res = this.createEditModelAndView(serviceForm);

		return res;
	}

	// Edition -------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int serviceId) {

		Assert.isTrue(this.managerService.checkAuthority());

		ModelAndView res;

		final Service service = this.serviceService.findOneToEdit(serviceId);
		final ServiceForm serviceForm = this.serviceService.construct(service);

		res = this.createEditModelAndView(serviceForm);

		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final ServiceForm serviceForm, final BindingResult binding) {

		ModelAndView res;
		Service service = this.serviceService.reconstruct(serviceForm, binding);
		if (binding.hasErrors())
			res = this.createEditModelAndView(serviceForm, "service.params.error");
		else
			try {
				this.serviceService.save(service);
				res = new ModelAndView("redirect:/service/manager/list.do");
			} catch (final Throwable oops) {
				System.out.println(oops);
				System.out.println(binding);
				res = this.createEditModelAndView(serviceForm, "service.commit.error");
			}

		return res;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(final int serviceId) {

		ModelAndView res;
		final Service service = this.serviceService.findOne(serviceId);
		final ServiceForm serviceForm = this.serviceService.construct(service);

		if (!service.getRequests().isEmpty())
			res = this.createEditModelAndView(serviceForm, "service.used");
		else
			try {
				this.serviceService.delete(service);
				res = new ModelAndView("redirect:/service/manager/list.do");
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(serviceForm, "service.commit.error");
			}

		return res;
	}

	// Ancillary method ----------------

	protected ModelAndView createEditModelAndView(final ServiceForm serviceForm) {
		ModelAndView res;

		res = this.createEditModelAndView(serviceForm, null);

		return res;
	}

	protected ModelAndView createEditModelAndView(final ServiceForm serviceForm, final String message) {
		ModelAndView res;
		Collection<Category> categories = new ArrayList<Category>();
		categories = this.categoryService.findAll();

		res = new ModelAndView("service/edit");
		res.addObject("serviceForm", serviceForm);
		res.addObject("categories", categories);
		res.addObject("message", message);
		res.addObject("requestURI", "service/manager/edit.do");

		return res;
	}
}
