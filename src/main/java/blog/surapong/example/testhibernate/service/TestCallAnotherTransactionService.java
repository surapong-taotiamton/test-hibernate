package blog.surapong.example.testhibernate.service;

import blog.surapong.example.testhibernate.repository.PeopleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TestCallAnotherTransactionService {

    @Autowired
    private TestTransactionPeopleService testTransactionPeopleService;

    @Autowired
    private PeopleRepository peopleRepository;

    public void testCallAnotherTransaction(String fullName, String address) {

        log.info("Before call another");
        testTransactionPeopleService.createPeopleDefaultTransactional(fullName, address, false);
        log.info("After call another");

    }


    public void testWithOutTransaction(String fullName, String address) {
        log.info("Before call another");
        testTransactionPeopleService.createWithOutTransactional(fullName, address, false);
        log.info("After call another");
    }
}
