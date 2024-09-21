package hello.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

@Slf4j
@RestController
public class TrafficController {
    @GetMapping("cpu")
    public String cpu() {
        log.info("cpu log");
        long value = 0;
        for (int i = 0; i < 1000000; i++) {
            value += i;
        }
        return "ok value=" + value;
    }

    private List<String> list = new ArrayList<>();

    @GetMapping("jvm")
    public String jvm() {
        log.info("jvm log");
        for (int i = 0; i < 1000000; i++) {
            list.add("hello jvm!" + i);
        }
        return "ok";
    }

    @Autowired
    DataSource dataSource;

    @GetMapping("jdbc")
    public String jdbc() throws SQLException {
        log.info("jdbc log");
        Connection conn = dataSource.getConnection();
        log.info("conn={}", conn);
        // conn.close(); // 커넥션을 닫지 않는다.
        return "ok";
    }

    @GetMapping("error-log")
    public String errorLog() {
        log.error("error log");
        return "error";
    }
}
