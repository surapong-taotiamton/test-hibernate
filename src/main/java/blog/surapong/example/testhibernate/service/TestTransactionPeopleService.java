package blog.surapong.example.testhibernate.service;

import blog.surapong.example.testhibernate.entity.People;
import blog.surapong.example.testhibernate.repository.PeopleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class TestTransactionPeopleService {

    @Autowired
    private PeopleRepository peopleRepository;

    @Transactional
    public People createPeopleDefaultTransactional(String fullName, String address, boolean throwError) {
        log.info("Before call internalCreate");
        People people = internalCreate(fullName, address, throwError);
        log.info("After call internalCreate");
        return people;
    }


    public People createWithOutTransactional(String fullName, String address, boolean throwError) {
        log.info("Before call internalCreate");
        People people = internalCreate(fullName, address, throwError);
        log.info("After call internalCreate");
        return people;
    }

    protected People internalCreate(String fullName, String address, boolean throwError) {
        People people = new People()
                .setAddress(address)
                .setFullName(fullName);

        log.info("Before save with repository");
        people = peopleRepository.save(people);
        log.info("After save with repository");

        if (throwError) {
            throw new RuntimeException("throw error for test transactional");
        }
        return people;
    }

}
