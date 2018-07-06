
package controllers.admin;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import security.UserAccount;
import services.ActorService;
import services.AdminService;
import services.ManagerService;
import services.RangerService;
import services.UserAccountService;
import controllers.AbstractController;
import domain.Manager;
import domain.Ranger;

@Controller
@RequestMapping("/actor/admin")
public class ActorAdminController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	public ManagerService		managerService;

	@Autowired
	public RangerService		rangerService;

	@Autowired
	public AdminService			adminService;

	@Autowired
	public ActorService			actorService;

	@Autowired
	public UserAccountService	userAccountService;


	// Constructor -------------------------------------------------------------
	public ActorAdminController() {
		super();
	}

	// Creating  --------------------------------------------------

	// Listing ---------------------------------------------------------

	@RequestMapping(value = "/listSuspicious", method = RequestMethod.GET)
	public ModelAndView listSuspicious() {
		ModelAndView result;

		result = this.createEditModelAndView();
		return result;
	}

	private ModelAndView createEditModelAndView() {
		ModelAndView result;
		Collection<Ranger> rangers;
		Collection<Manager> managers;

		rangers = this.adminService.findSuspiciousRangers();
		managers = this.adminService.findSuspiciousManagers();

		result = new ModelAndView("admin/listSuspicious");
		result.addObject("rangers", rangers);
		result.addObject("managers", managers);

		return result;
	}

	@RequestMapping(value = "/desactivate-ranger", method = RequestMethod.GET)
	public ModelAndView desactivateRanger(@RequestParam final int rangerId) {
		ModelAndView result = null;
		Ranger ranger;
		UserAccount userAccount;
		ranger = this.rangerService.findOne(rangerId);
		Assert.notNull(ranger);

		try {
			userAccount = ranger.getUserAccount();
			userAccount.setEnable(false);
			this.userAccountService.save(userAccount);

			result = this.createEditModelAndView();

		} catch (final Throwable oops) {
			result.addObject("message", "admin.commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/desactivate-manager", method = RequestMethod.GET)
	public ModelAndView desactivateManager(@RequestParam final int managerId) {
		ModelAndView result = null;
		Manager manager;
		UserAccount userAccount;
		manager = this.managerService.findOne(managerId);

		try {
			userAccount = manager.getUserAccount();
			userAccount.setEnable(false);
			this.userAccountService.save(userAccount);

			result = this.createEditModelAndView();

		} catch (final Throwable oops) {
			result.addObject("message", "admin.commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/activate-ranger", method = RequestMethod.GET)
	public ModelAndView activateRanger(@RequestParam final int rangerId) {
		ModelAndView result = null;
		Ranger ranger;
		UserAccount userAccount;
		ranger = this.rangerService.findOne(rangerId);

		try {
			ranger.setSuspicious(false);
			userAccount = ranger.getUserAccount();
			userAccount.setEnable(true);
			this.userAccountService.save(userAccount);

			result = this.createEditModelAndView();

		} catch (final Throwable oops) {
			result.addObject("message", "admin.commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/activate-manager", method = RequestMethod.GET)
	public ModelAndView activateManager(@RequestParam final int managerId) {
		ModelAndView result = null;
		Manager manager;
		UserAccount userAccount;
		manager = this.managerService.findOne(managerId);

		try {
			manager.setSuspicious(false);
			userAccount = manager.getUserAccount();
			userAccount.setEnable(true);
			this.userAccountService.save(userAccount);

			result = this.createEditModelAndView();

		} catch (final Throwable oops) {
			result.addObject("message", "admin.commit.error");
		}

		return result;
	}

	// Ancillary methods ------------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Manager user) {
		ModelAndView result;

		result = this.createEditModelAndView(user, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Manager manager, final String message) {
		ModelAndView result;
		Collection<Authority> authorities;
		Authority authorityManager;

		authorityManager = new Authority();
		authorities = new ArrayList<>();
		authorityManager.setAuthority(Authority.MANAGER);
		authorities.add(authorityManager);

		result = new ModelAndView("manager/admin/register");
		result.addObject("manager", manager);
		result.addObject("authorities", authorities);
		result.addObject("message", message);

		return result;
	}
}
