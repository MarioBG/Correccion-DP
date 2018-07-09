
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.BankAgentRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.BankAccount;
import domain.BankAgent;
import domain.Comment;
import domain.Folder;
import domain.GovernmentAgent;
import forms.BankAgentForm;

@Service
@Transactional
public class BankAgentService {

	// Managed repository

	@Autowired
	private BankAgentRepository			bankAgentRepository;

	// Supporting services

	@Autowired
	private security.UserAccountService	userAccountService;
	@Autowired
	private ActorService				actorService;
	@Autowired
	private GovernmentAgentService		governmentAgentService;

	@Autowired
	private FolderService				folderService;

	@Autowired
	private ConfigurationService		configurationService;

	@Autowired
	private Validator					validator;


	// Constructors

	public BankAgentService() {
		super();
	}

	// Simple CRUD methods

	public BankAgent create() {

		final BankAgent res = new BankAgent();

		final UserAccount agentAccount = new UserAccount();
		final Authority authority = new Authority();
		final Collection<Folder> folders = new ArrayList<Folder>();
		final Collection<Comment> comments = new ArrayList<Comment>();
		final Collection<BankAccount> bankAccounts = new ArrayList<BankAccount>();
		authority.setAuthority(Authority.BANKAGENT);
		agentAccount.addAuthority(authority);
		res.setUserAccount(agentAccount);

		res.setFolders(folders);
		res.setComments(comments);
		res.setCarriedBankAccounts(bankAccounts);

		return res;
	}

	public Collection<BankAgent> findAll() {
		Collection<BankAgent> res;
		res = this.bankAgentRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public BankAgent findOne(final int agentId) {
		Assert.isTrue(agentId != 0);
		BankAgent res;
		res = this.bankAgentRepository.findOne(agentId);
		Assert.notNull(res);
		return res;
	}

	public BankAgent save(final BankAgent agent) {
		BankAgent res;
		if (agent.getId() == 0) {
			Assert.notNull(this.governmentAgentService.findByPrincipal());
			if (agent.getPhone().matches("^\\d+$"))
				agent.setPhone(this.configurationService.findActive().getDefaultCountryCode() + agent.getPhone());
			final Collection<Folder> folders = this.folderService.save(this.folderService.defaultFolders());
			agent.setFolders(folders);
			agent.getUserAccount().setPassword(new Md5PasswordEncoder().encodePassword(agent.getUserAccount().getPassword(), null));
			Assert.isTrue(this.userAccountService.findByUsername(agent.getUserAccount().getUsername()) == null, "message.error.duplicatedUser");
		}
		res = this.bankAgentRepository.save(agent);

		return res;
	}

	// Other business methods

	public BankAgent findBankAgentByUserAccountId(final int uA) {
		return this.bankAgentRepository.findBankAgentByUserAccountId(uA);
	}

	public BankAgent findByPrincipal() {
		BankAgent res;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		if (userAccount == null)
			res = null;
		else
			res = this.bankAgentRepository.findBankAgentByUserAccountId(userAccount.getId());
		return res;
	}

	public BankAgent reconstruct(final BankAgentForm bankAgentForm, final BindingResult binding) {

		Assert.notNull(bankAgentForm);
		Assert.isTrue(bankAgentForm.getTermsAndConditions(), "user.params.errorTerms");

		BankAgent res = new BankAgent();

		if (bankAgentForm.getId() != 0)
			res = this.findOne(bankAgentForm.getId());
		else
			res = this.create();

		res.setName(bankAgentForm.getName());
		res.setSurname(bankAgentForm.getSurname());
		res.setEmail(bankAgentForm.getEmail());
		res.setPhone(bankAgentForm.getPhone());
		res.setAddress(bankAgentForm.getAddress());
		res.setBankCode(bankAgentForm.getBankCode());
		res.setCanCreateMoney(bankAgentForm.getCanCreateMoney());
		res.getUserAccount().setUsername(bankAgentForm.getUsername());
		res.getUserAccount().setPassword(bankAgentForm.getPassword());
		if (bankAgentForm.getId() != 0)
			res.setNif(this.findOne(bankAgentForm.getId()).getNif());
		else
			res.setNif(this.actorService.generateNif(this.governmentAgentService.findByPrincipal()));

		if (binding != null)
			this.validator.validate(res, binding);

		return res;
	}

	public BankAgentForm construct(final BankAgent agent) {

		Assert.notNull(agent);
		final BankAgentForm editAgentForm = new BankAgentForm();

		editAgentForm.setId(agent.getId());
		editAgentForm.setName(agent.getName());
		editAgentForm.setSurname(agent.getSurname());
		editAgentForm.setEmail(agent.getEmail());
		editAgentForm.setPhone(agent.getPhone());
		editAgentForm.setAddress(agent.getAddress());
		editAgentForm.setBankCode(agent.getBankCode());
		editAgentForm.setCanCreateMoney(agent.getCanCreateMoney());
		editAgentForm.setUsername(agent.getUserAccount().getUsername());
		editAgentForm.setPassword(agent.getUserAccount().getPassword());
		editAgentForm.setRepeatPassword(agent.getUserAccount().getPassword());
		editAgentForm.setTermsAndConditions(false);

		return editAgentForm;
	}

	public void flush() {
		this.bankAgentRepository.flush();
	}

	public void stopCreateMoney(int agentId) {
		GovernmentAgent governmentAgent = (GovernmentAgent) this.actorService.findByPrincipal();
		Assert.notNull(governmentAgent);
		BankAgent agent = this.bankAgentRepository.findOne(agentId);
		if (agent.getCanCreateMoney()) {
			agent.setCanCreateMoney(false);
		}

	}

}
