package com.nhnacademy.edu.springframework.project.repository;

public class WaterBill {
    private String city;
    private String sector;
    private Integer level;
    private Integer start;
    private Integer end;
    private Long unitPrice;
    private Long billTotal;

    public WaterBill(String city, String sector, Integer level, Integer start, Integer end, Long unitPrice) {
        this.city = city;
        this.sector = sector;
        this.level = level;
        this.start = start;
        this.end = end;
        this.unitPrice = unitPrice;
    }

    public Integer getLevel() {
        return level;
    }

    public Integer getStart() {
        return start;
    }

    public Integer getEnd() {
        return end;
    }

    public Long getUnitPrice() {
        return unitPrice;
    }

    public Long getBillTotal() {
        return billTotal;
    }

    public void setBillTotal(long billTotal) {
        this.billTotal = billTotal;
    }

    @Override
    public String toString() {
        return "WaterBill{" +
                "city='" + city + '\'' +
                ", sector='" + sector + '\'' +
                ", unitPrice=" + unitPrice +
                ", billTotal=" + billTotal +
                '}';
    }
}
