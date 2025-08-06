package org.scoula.config.db;

import lombok.extern.slf4j.Slf4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RdsConnectionCleaner {

    private final DataSource dataSource;

    public RdsConnectionCleaner(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 5분마다 오래된 Sleep 세션 종료
     */
    @Scheduled(fixedDelay = 300000) // 5분마다 실행
    public void cleanOldSleepConnections() {
        String selectSql = """
            SELECT ID
            FROM information_schema.PROCESSLIST
            WHERE COMMAND='Sleep' AND TIME > 300
        """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(selectSql);
             ResultSet rs = ps.executeQuery()) {

            int killCount = 0;

            while (rs.next()) {
                long id = rs.getLong("ID");
                String killSql = "KILL " + id;
                try (Statement killStmt = conn.createStatement()) {
                    killStmt.execute(killSql);
                    killCount++;
                    log.warn("Killed Sleep connection ID: {}", id);
                } catch (Exception e) {
                    log.error("Failed to kill connection ID " + id, e);
                }
            }

            if (killCount > 0) {
                log.warn("Total killed old Sleep connections: {}", killCount);
            }

        } catch (Exception e) {
            log.error("Error while cleaning old Sleep connections", e);
        }
    }
}
