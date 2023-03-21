package blog.surapong.example.testhibernate.service;

import blog.surapong.example.testhibernate.entity.People;
import blog.surapong.example.testhibernate.repository.PeopleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class TestReadOnlyService {

    @Autowired
    private PeopleRepository peopleRepository;

    @Transactional(readOnly = true)
    public People testReadOnly(Long id) {
        log.info("Begin get readOnly");
        People people = peopleRepository.findById(id).orElseThrow();
        log.info("After get readOnly");

        people.setFullName("FULL NAME IN READ ONLY");
        people.setAddress("ADDRESS IN READ ONLY");

        return people;
    }

    @Transactional(readOnly = false)
    public People testNotReadOnly(Long id) {
        log.info("Begin get not readOnly");
        People people = peopleRepository.findById(id).orElseThrow();
        log.info("After get not readOnly ");

        log.info("Begin change data");
        people.setFullName("FULL NAME IN not readOnly ");
        people.setAddress("ADDRESS IN not readOnly");
        log.info("After change data");

        return people;
    }


}
