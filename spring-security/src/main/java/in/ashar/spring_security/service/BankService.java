package in.ashar.spring_security.service;

import in.ashar.spring_security.entity.Bank;
import in.ashar.spring_security.repository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankService {

    @Autowired
    private BankRepository bankRepository;

    public Bank createBank(Bank bank) {
        return bankRepository.save(bank);
    }


    public Bank getBankById(int id) {
        return bankRepository.findById(id).orElseThrow(RuntimeException::new);
    }


    public List<Bank> getAllBanks() {
        return bankRepository.findAll();
    }

    public Bank updateBank(int id, Bank bank) {
        Bank existing = bankRepository.findById(id).orElseThrow(RuntimeException::new);
        bank.setBankId(existing.getBankId());
        return bankRepository.save(bank);
    }

    public void deleteBank(int id) {
        bankRepository.deleteById(id);
    }
}
