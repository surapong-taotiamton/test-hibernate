package blog.surapong.example.testhibernate.controller;

import blog.surapong.example.testhibernate.service.TestTransactionPeopleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TestSeparateTransactionController {

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private TestTransactionPeopleService testTransactionPeopleService;

    @GetMapping("/test-separate/complete-flow")
    public void testCompleteFlow() {
        log.info("Before call A");
        testTransactionPeopleService.createPeopleDefaultTransactional("A", "1", false);
        log.info("After call A");

        log.info("Before call B");
        testTransactionPeopleService.createPeopleDefaultTransactional("B", "2", false);
        log.info("After call B");
    }


    @GetMapping("/test-separate/last-fail")
    public void testLastFail() {
        log.info("Before call C");
        testTransactionPeopleService.createPeopleDefaultTransactional("C", "3", false);
        log.info("After call C");

        log.info("Before call D");
        testTransactionPeopleService.createPeopleDefaultTransactional("D", "4", true);
        log.info("After call D");
    }


    @GetMapping("/test-separate/combine-complete")
    public void combineTransactionComplete() {

        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);

        transactionTemplate.executeWithoutResult(status -> {
            log.info("Before call A");
            testTransactionPeopleService.createPeopleDefaultTransactional("A", "1", false);
            log.info("After call A");

            log.info("Before call B");
            testTransactionPeopleService.createPeopleDefaultTransactional("B", "2", false);
            log.info("After call B");
        });

    }

    @GetMapping("/test-separate/combine-last-fail")
    public void combineTransactionLastFail() {

        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.executeWithoutResult(transactionStatus -> {

            log.info("Before call C");
            testTransactionPeopleService.createPeopleDefaultTransactional("C", "3", false);
            log.info("After call C");

            log.info("Before call D");
            testTransactionPeopleService.createPeopleDefaultTransactional("D", "4", true);
            log.info("After call D");
        });
    }
}
