/*
 * SampleTest.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Service;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ServiceServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private ServiceService	serviceService;


	// Tests ------------------------------------------------------------------

	@Test
	public void driver() {
		final Object testingEditData[][] = {

			{
				"manager1", "service1", null
			}, {
				null, "service2", IllegalArgumentException.class
			}, // Un anonimo no puede editar un servicio
			{
				"manager2", "service2", null
			}, {
				"user1", "service1", IllegalArgumentException.class
			}, // Un usuario no puede editar un servicio
			{
				"manager1", "service3", null
			}, {
				"manager1", "serviceNotExisting", NumberFormatException.class
			}, // Un manager no puede editar un servicio que no existe
			{
				"manager3", "service5", null
			},

			{
				"manager2", "service4", IllegalArgumentException.class
			}
		// Un manager no puede editar un servicio cancelado
		};

		final Object testingDeleteData[][] = {
			{
				"manager1", "service3", null
			}, {
				null, "service2", IllegalArgumentException.class
			},					// Un anonimo no puede eliminar un servicio
			{
				"manager2", "service6", null
			}, {
				"user1", "service1", IllegalArgumentException.class
			},				// Un usuario no puede eliminar un servicio
			{
				"manager3", "service7", null
			}, {
				"manager3", "service5", IllegalArgumentException.class
			},				// Un manager no puede eliminar un servicio que contenga peticiones
		};

		for (int i = 0; i < testingEditData.length; i++)
			this.templateEdit((String) testingEditData[i][0], (String) testingEditData[i][1], (Class<?>) testingEditData[i][2]);

		for (int i = 0; i < testingDeleteData.length; i++)
			this.templateDelete((String) testingDeleteData[i][0], (String) testingDeleteData[i][1], (Class<?>) testingDeleteData[i][2]);
	}

	// Ancillary methods ------------------------------------------------------

	protected void templateEdit(final String authenticate, final String beanName, final Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {
			final int serviceId = super.getEntityId(beanName);
			super.authenticate(authenticate);
			final Service service = this.serviceService.findOneToEdit(serviceId);
			service.setName("Service name changed");
			this.serviceService.save(service);
			this.serviceService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void templateDelete(final String authenticate, final String beanName, final Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {
			final int serviceId = super.getEntityId(beanName);
			super.authenticate(authenticate);
			final Service service = this.serviceService.findOneToEdit(serviceId);
			this.serviceService.delete(service);
			this.serviceService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

}
