package blog.surapong.example.testhibernate.controller;

import blog.surapong.example.testhibernate.service.TestCallAnotherTransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TestCallAnotherTransactionController {

    @Autowired
    private TestCallAnotherTransactionService testCallAnotherTransactionService;

    @GetMapping("/test-call-another/transactional")
    public void testCallAnotherTransactional() {
        log.info("Before call testCallAnotherTransaction");
        testCallAnotherTransactionService.testCallAnotherTransaction("F", "5");
        log.info("After call testCallAnotherTransaction");
    }

    @GetMapping("/test-call-another/not-transactional")
    public void testCallWithOutTransactional() {
        log.info("Before call testCallWithOutTransactional");
        testCallAnotherTransactionService.testWithOutTransaction("G", "6");
        log.info("After call testCallWithOutTransactional");
    }




}
