package com.ymd.eureka;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

@SpringBootTest
class EurekaApplicationTests {

    @Test
    public void setTest() throws SQLException {
        ResultSet rs = (ResultSet) new HashSet<String>();

        System.out.println(rs.next());
        Set<String> result = new HashSet<>();
        result.add("aa");
        result.add("bb");
        result.add("aa");
        System.out.println(result);
    }

}
