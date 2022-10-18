package com.nhnacademy.edu.springframework.project.service;

import com.nhnacademy.edu.springframework.project.config.JavaConfig;
import com.nhnacademy.edu.springframework.project.repository.WaterBill;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = JavaConfig.class)
class DefaultResultReportTest {

    @Autowired
    DefaultResultReport defaultResultReport;

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    void init() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    @DisplayName("billTotal을 기준으로 오름차순 정렬 한 뒤 5개를 출력한다.")
    void report() throws Exception {
        //given
        List<WaterBill> list = new ArrayList<>();
        Long usage = 1000L;
        for (int i = 10; i >= 1; i--) {
            WaterBill waterBill = new WaterBill("시티" + i, "섹터" + i, i, i, i, 100L * i);
            waterBill.setBillTotal(usage * waterBill.getUnitPrice());
            list.add(waterBill);
        }

        //when
        defaultResultReport.report(list);

        //then
        Assertions.assertEquals("WaterBill{city='시티1', sector='섹터1', unitPrice=100, billTotal=100000}\n" +
                "WaterBill{city='시티2', sector='섹터2', unitPrice=200, billTotal=200000}\n" +
                "WaterBill{city='시티3', sector='섹터3', unitPrice=300, billTotal=300000}\n" +
                "WaterBill{city='시티4', sector='섹터4', unitPrice=400, billTotal=400000}\n" +
                "WaterBill{city='시티5', sector='섹터5', unitPrice=500, billTotal=500000}", outputStreamCaptor.toString()
                .trim());
    }

    @AfterEach
    void tearDown() {
        System.setOut(standardOut);
    }
}