
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.RendezvousService;
import services.ServiceService;
import domain.Rendezvous;
import domain.Service;

@Controller
@RequestMapping("/service")
public class ServiceController extends AbstractController {

	// Services ---------------

	@Autowired
	private ServiceService		serviceService;

	@Autowired
	private RendezvousService	rendezvousService;


	// Constructors -----------

	public ServiceController() {
		super();
	}

	// Listing ----------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) final Integer rendezvousId) {

		ModelAndView result;
		Collection<Service> services;
		Rendezvous rendezvous = null;

		if (rendezvousId == null)
			services = this.serviceService.findAll();
		else {
			rendezvous = this.rendezvousService.findOne(rendezvousId);
			services = this.serviceService.findByRendezvousId(rendezvousId);
		}

		result = new ModelAndView("service/list");
		result.addObject("services", services);
		result.addObject("rendezvous", rendezvous);
		result.addObject("requestURI", "service/list.do");

		return result;
	}
	// Display -----------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int serviceId) {

		ModelAndView result;

		final Service service = this.serviceService.findOne(serviceId);

		result = new ModelAndView("service/display");
		result.addObject("service", service);

		return result;
	}

}
