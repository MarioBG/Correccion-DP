
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.LotteryTicket;

@Repository
public interface LotteryTicketRepository extends JpaRepository<LotteryTicket, Integer> {

	@Query("select l from LotteryTicket l where l.citizen.id = ?1")
	Collection<LotteryTicket> findAllByCitizenId(int id);

	@Query("select l from LotteryTicket l where l.lottery.id = ?2 and l.number = ?1")
	LotteryTicket findByNumberAndLotteryId(int number, int lotteryId);

}
