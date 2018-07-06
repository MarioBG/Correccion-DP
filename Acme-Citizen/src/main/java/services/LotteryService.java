
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.LotteryRepository;
import domain.Actor;
import domain.Citizen;
import domain.GovernmentAgent;
import domain.Lottery;
import domain.LotteryTicket;

@Service
@Transactional()
public class LotteryService {

	// Managed repository

	@Autowired
	private LotteryRepository	lotteryRepository;

	// Supporting services

	@Autowired
	private ActorService		actorService;


	// Constructors

	public LotteryService() {
		super();
	}

	// Simple CRUD methods

	public Lottery create() {

		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(principal != null);

		final Lottery result = new Lottery();

		final Collection<LotteryTicket> lotterys = new ArrayList<LotteryTicket>();
		result.setLotteryTickets(lotterys);
		result.setGovernmentAgent((GovernmentAgent) principal);
		result.setQuantity(0);

		return result;
	}

	public Lottery save(final Lottery lottery) {
		Assert.notNull(lottery);
		Lottery result;

		final Date date = new Date();
		if (lottery.getCelebrationDate().before(date))
			Assert.isTrue(lottery.getCelebrationDate().after(date), "commit.error.date");
		result = this.lotteryRepository.save(lottery);
		return result;
	}

	public Collection<Lottery> findAll() {

		final Collection<Lottery> result = this.lotteryRepository.findAll();
		return result;
	}

	public Lottery findOne(final int lotteryId) {

		Assert.isTrue(lotteryId != 0);

		final Lottery result = this.lotteryRepository.findOne(lotteryId);
		return result;
	}

	public Collection<Lottery> findAllAfterToday() {
		return this.lotteryRepository.findAllAfterToday();
	}

	public Collection<Lottery> getLotteryByGovernmentAgentId(final int id) {
		return this.lotteryRepository.getLotteryByGovernmentAgentId(id);
	}

	public Collection<Lottery> getLotteryWinner(final int id) {
		return this.lotteryRepository.getLotteryWinner(id);
	}

	public void delete(final Lottery lottery) {
		Assert.notNull(lottery);
		this.delete(lottery);

	}

	public void lotteryWinner(final int lotteryId) {
		final Lottery lottery = this.findOne(lotteryId);
		final Collection<Lottery> lottos = this.getLotteryByGovernmentAgentId(lottery.getGovernmentAgent().getId());
		final List<LotteryTicket> lista = new ArrayList<LotteryTicket>(lottery.getLotteryTickets());

		if (lottos.size() > 0)
			if (lottery.getWinnerTicket() == null && lottos.contains(lottery)) {
				final Integer num1 = (int) (Math.random() * lista.size());

				final LotteryTicket winner = lista.get(num1);
				lottery.setWinnerTicket(winner);
				this.moneyWon(lottery, winner);

			}

	}

	public void moneyWon(final Lottery lotto, final LotteryTicket lt) {
		double winCitizen = 0.0;
		double winAgent = 0.0;
		final int tam = lotto.getLotteryTickets().size();
		final double precio = lotto.getTicketCost();
		final double cantidad = precio * tam;
		final double percentage = lotto.getPercentageForPrizes();

		winCitizen = (cantidad * percentage) / 100;
		winAgent = cantidad - winCitizen;

		final Citizen citizen = (Citizen) this.actorService.findOne(lt.getCitizen().getId());
		final GovernmentAgent ga = (GovernmentAgent) this.actorService.findOne(lotto.getGovernmentAgent().getId());

		// this.lotteryRepository.flush();
		final Double newMoneyCitizen = citizen.getBankAccount().getMoney() + winCitizen;
		final Double newMoneyGa = ga.getBankAccount().getMoney() + winAgent;

		citizen.getBankAccount().setMoney(newMoneyCitizen);
		ga.getBankAccount().setMoney(newMoneyGa);

	}

	public void flush() {
		this.lotteryRepository.flush();
	}

}
