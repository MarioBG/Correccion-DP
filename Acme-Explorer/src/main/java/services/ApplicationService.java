
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ApplicationRepository;
import domain.Application;
import domain.CreditCard;
import domain.Explorer;
import domain.Manager;
import domain.Status;
import domain.Trip;

@Service
@Transactional
public class ApplicationService {

	// Managed Repository -----------------------------------------------------

	@Autowired
	private ApplicationRepository	applicationRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ExplorerService			explorerService;

	@Autowired
	private ManagerService			managerService;

	@Autowired
	private MessageService			messageService;

	@Autowired
	private TripService				tripService;


	// Simple CRUD methods ----------------------------------------------------

	public Application create(final Explorer explorer) {
		Application application;

		application = new Application();
		application.setMoment(new Date(System.currentTimeMillis() - 1000));
		application.setStatus(Status.PENDING);
		application.setApplicant(explorer);

		return application;
	}

	public Application findOne(final int applicationId) {
		Application application = null;

		application = this.applicationRepository.findOne(applicationId);
		Assert.isTrue(this.checkByPrincipalManager(application) || this.checkByPrincipalExplorer(application));

		return application;
	}
	public Application save(final Application application) {
		Assert.notNull(application);
		Assert.notNull(application.getTrip());
		Assert.isTrue(application.getTrip().getPublicationDate().before(new Date()) && application.getTrip().getStartDateTrip().after(new Date()));

		Application saved = null;

		if (application.getId() == 0)
			Assert.isTrue(!this.tripService.hasThisTripAnyApplicationFromThisExplorer(application.getTrip().getId(), application.getApplicant().getId()));
		else if (application.getStatus().equals(Status.DUE) && application.getCreditCard() != null) {
			Assert.notNull(application.getCreditCard().getHolder());
			Assert.notNull(application.getCreditCard().getBrand());
			Assert.notNull(application.getCreditCard().getNumber());
			Assert.notNull(application.getCreditCard().getExpirationMonth());
			Assert.notNull(application.getCreditCard().getExpirationYear());
			Assert.notNull(application.getCreditCard().getCvv());
			application.setStatus(Status.ACCEPTED);
		} else if (application.getStatus().equals(Status.PENDING))
			Assert.isNull(application.getRejectReason());

		saved = this.applicationRepository.save(application);

		return saved;
	}

	public Collection<Application> findAll() {
		return this.applicationRepository.findAll();
	}

	public void delete(final Application application) {
		this.applicationRepository.delete(application);
	}

	public void deleteApplications(final Trip trip) {
		final Collection<Application> applications = trip.getApplications();
		this.applicationRepository.delete(applications);
	}

	// Other business methods -------------------------------------------------

	public void dueApplication(final Application application) {

		Assert.notNull(application);
		Assert.isTrue(application.getStatus().equals(Status.PENDING));
		this.checkByPrincipalManager(application);

		Status oldStatus = null;

		oldStatus = application.getStatus();

		// Al ejecutar setStatus, ya persiste en la base de datos 

		application.setStatus(Status.DUE);

		this.messageService.notifyChangeInApplicationStatus(oldStatus, application);
	}

	public void cancelApplication(final Application application) {

		Assert.notNull(application);
		Assert.isTrue(application.getStatus().equals(Status.ACCEPTED));
		Assert.isTrue(application.getTrip().getStartDateTrip().after(new Date(System.currentTimeMillis() - 1000)));   //Requisito 13.4

		Date currentDate = null;
		Status oldStatus = null;

		currentDate = new Date(System.currentTimeMillis() - 10000);
		Assert.isTrue(currentDate.before(application.getTrip().getStartDateTrip()));
		this.checkByPrincipalExplorer(application);

		oldStatus = application.getStatus();

		// Al ejecutar setStatus, ya persiste en la base de datos

		application.setStatus(Status.CANCELLED);

		this.messageService.notifyChangeInApplicationStatus(oldStatus, application);
	}
	public void acceptApplication(final Application application, final CreditCard creditCard) {

		this.checkByPrincipalExplorer(application);

		Status oldStatus = null;

		Assert.notNull(application);
		Assert.isTrue(application.getStatus().equals(Status.DUE));

		Assert.notNull(creditCard);
		Assert.notNull(creditCard.getHolder());
		Assert.notNull(creditCard.getBrand());
		Assert.notNull(creditCard.getExpirationMonth());
		Assert.notNull(creditCard.getExpirationYear());
		Assert.notNull(creditCard.getCvv());

		application.setCreditCard(creditCard);
		oldStatus = application.getStatus();

		// Al ejecutar setStatus, ya persiste en la base de datos

		application.setStatus(Status.ACCEPTED);

		this.messageService.notifyChangeInApplicationStatus(oldStatus, application);
	}

	public void rejectApplication(final Application application, final String rejectReason) {

		Assert.notNull(application);
		Assert.notNull(rejectReason);
		Assert.isTrue(!rejectReason.isEmpty());
		Assert.isTrue(application.getStatus().equals(Status.PENDING));

		this.checkByPrincipalManager(application);

		final Status oldStatus = application.getStatus();

		// Al ejecutar setStatus, ya persiste en la base de datos

		application.setStatus(Status.REJECTED);
		application.setRejectReason(rejectReason);

		this.messageService.notifyChangeInApplicationStatus(oldStatus, application);
	}

	public Collection<Application> findApplicationsByExplorerId(final int explorerId) {
		return this.applicationRepository.findApplicationByExplorer(explorerId);
	}

	public Collection<Application> findApplicationsByManagerId(final int managerId) {
		return this.applicationRepository.findApplicationByManager(managerId);
	}

	public String findRatioOfAcceptedApplications() {
		return this.applicationRepository.acceptedApplications();
	}

	public String findRatioOfCancellededApplications() {
		return this.applicationRepository.cancelledApplications();
	}

	public String findRatioOfDueApplications() {
		return this.applicationRepository.dueApplications();
	}

	public String findRatioOfPendingApplications() {
		return this.applicationRepository.pendingApplications();
	}

	public Collection<Application> findApplicationsByStatus(final Status status) {
		return this.applicationRepository.findApplicationsByStatus(status);
	}

	private boolean checkByPrincipalManager(final Application application) {
		Boolean res = false;

		Manager manager = null;

		manager = this.managerService.findByPrincipal();
		if (application.getTrip().getManager().equals(manager))
			res = true;
		return res;

	}

	private boolean checkByPrincipalExplorer(final Application application) {
		Boolean res = false;

		Explorer explorer = null;

		explorer = this.explorerService.findByPrincipal();
		if (application.getApplicant().equals(explorer))
			res = true;
		return res;

	}

}
