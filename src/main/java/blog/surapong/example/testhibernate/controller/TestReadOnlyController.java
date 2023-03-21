package blog.surapong.example.testhibernate.controller;

import blog.surapong.example.testhibernate.entity.People;
import blog.surapong.example.testhibernate.repository.PeopleRepository;
import blog.surapong.example.testhibernate.service.TestReadOnlyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TestReadOnlyController {

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private PeopleRepository peopleRepository;

    @Autowired
    private TestReadOnlyService testReadOnlyService;

    @GetMapping("/test-read-only/read-only")
    public void testReadOnly( @Param("id") Long id) {
        log.info("Before read only");
        People people  = testReadOnlyService.testReadOnly(id);
        log.info("After read only");
    }

    @GetMapping("/test-read-only/not-read-only")
    public void testNotReadOnly( @Param("id") Long id) {
        log.info("Before not read only");
        People people  = testReadOnlyService.testNotReadOnly(id);
        log.info("After not read only");
    }

    @GetMapping("/test-read-only/test-mix-read-only")
    public void testMix(@Param("idReadOnly") Long idReadOnly, @Param("idNotReadOnly") Long idNotReadOnly ) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);

        transactionTemplate.executeWithoutResult(transactionStatus -> {

            log.info("Before read only");
            testReadOnlyService.testReadOnly(idReadOnly);
            log.info("After read only");

            log.info("Before not read only");
            testReadOnlyService.testNotReadOnly(idNotReadOnly);
            log.info("After not read only");

        });

    }


    @GetMapping("/test-read-only/test-mix-not-read-only")
    public void testMixNotReadOnly(@Param("idReadOnly") Long idReadOnly, @Param("idNotReadOnly") Long idNotReadOnly ) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.setReadOnly(true);
        transactionTemplate.executeWithoutResult(transactionStatus -> {

            log.info("Before read only");
            People people1 = testReadOnlyService.testReadOnly(idReadOnly);
            log.info("After read only");

            log.info("Before not read only");
            People people2 = testReadOnlyService.testNotReadOnly(idNotReadOnly);
            log.info("After not read only");

            log.info("Begin Save with repository");
            peopleRepository.save(people1);
            log.info("After Save with repository");

        });

    }


}
