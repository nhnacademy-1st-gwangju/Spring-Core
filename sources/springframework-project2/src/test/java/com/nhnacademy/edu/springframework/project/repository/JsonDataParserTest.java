package com.nhnacademy.edu.springframework.project.repository;

import com.nhnacademy.edu.springframework.project.config.JavaConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = JavaConfig.class)
class JsonDataParserTest {

    @Autowired
    JsonDataParser jsonDataParser;

    @Test
    @DisplayName("json 파일을 읽어와 list로 반환한다.")
    public void parse() throws Exception {
        //given
        String path = "src/main/resources/data/Tariff_20220331.json";

        //when
        List<WaterBill> list = jsonDataParser.parse(path);

        //then
        assertThat(list).isNotEmpty();
        assertThat(list.get(0)).isNotNull();
    }
}