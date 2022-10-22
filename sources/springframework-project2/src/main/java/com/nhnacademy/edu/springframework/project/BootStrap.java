package com.nhnacademy.edu.springframework.project;

import com.nhnacademy.edu.springframework.project.repository.ChargeRepository;
import com.nhnacademy.edu.springframework.project.repository.WaterBill;
import com.nhnacademy.edu.springframework.project.service.ResultReport;
import com.nhnacademy.edu.springframework.project.service.WaterChargeService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class BootStrap {

    public static void main(String[] args) throws IOException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("com.nhnacademy.edu.springframework.project.config");
        ChargeRepository chargeRepository = context.getBean(ChargeRepository.class);
        chargeRepository.load("path는 자동으로 지정됩니다.");

        WaterChargeService chargeService = context.getBean(WaterChargeService.class);

        ResultReport resultReport = context.getBean(ResultReport.class);

        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("(0 이하 숫자 입력 시 종료) >>> ");
            Long usage = sc.nextLong();
            if (usage <= 0) {
                break;
            }
            List<WaterBill> waterBillList = chargeService.calculate(usage);
            resultReport.report(waterBillList);
        }
    }
}
