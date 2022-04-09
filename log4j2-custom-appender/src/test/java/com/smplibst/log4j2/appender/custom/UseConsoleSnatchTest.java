package com.smplibst.log4j2.appender.custom;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * {@link ConsoleSnatchAppender}利用サンプルクラス.<br>
 * @since 1.0.0
 */
public class UseConsoleSnatchTest {

    /** ロガー. */
    private Logger logger = LogManager.getLogger(getClass());

    /**
     * 開始終了処理.<br>
     * <p>
     * 複数クラス実行時にクリアタイミングを同一に統一できるなら<br>
     * {@link BeforeEach}か{@link AfterEach}のどちらかで良い...<br>
     * </p>
     * @since 1.0.0
     */
    @BeforeEach
    @AfterEach
    void initDestory() {
        // 既にキャッシュ済みログを消す。
        ConsoleSnatchAppender.clear();
    }

    /**
     * test1.<br>
     * @since 1.0.0
     */
    @Test
    void useConsoleSnatchTest1() {

        // ログが存在しないことを確認する。
        assertNotExistsAllLog();

        // ログを出力する。
        this.logger.trace("test1-TRACE");
        this.logger.debug("test1-DEBUG");
        this.logger.info("test1-INFO");
        this.logger.warn("test1-WARN");
        this.logger.error("test1-ERROR");
        this.logger.fatal("test1-FATAL");

        // 全レベルのログが取得出来ること。
        assertExistsAllLog("test1");
    }

    /**
     * test2.<br>
     * @since 1.0.0
     */
    @Test
    void useConsoleSnatchTest2() {

        // ログが存在しないことを確認する。
        assertNotExistsAllLog();

        // ログを出力する。
        this.logger.trace("test2-TRACE");
        this.logger.debug("test2-DEBUG");
        this.logger.info("test2-INFO");
        this.logger.warn("test2-WARN");
        this.logger.error("test2-ERROR");
        this.logger.fatal("test2-FATAL");

        // 全レベルのログが取得出来ること。
        assertExistsAllLog("test2");
    }

    /**
     * 全ログレベルのログが存在しないことを確認する.<br>
     * @since 1.0.0
     */
    private static void assertNotExistsAllLog() {

        // 全ログを取得する。
        List<String> lines = ConsoleSnatchAppender.getLogs(Level.ALL);
        assertEquals(0, lines.size(), "全ログ数");
    }

    /**
     * 全レベルのログが存在することを確認する.<br>
     * @param prefix メッセージPrefix
     * @since 1.0.0
     */
    private static void assertExistsAllLog(String prefix) {

        // TRACE
        List<String> lines = ConsoleSnatchAppender.getLogs(Level.TRACE);
        assertEquals(1, lines.size(), "TRACEログ数");
        assertEquals(prefix + "-TRACE", lines.get(0), "ログメッセージ");

        // DEBUG
        lines = ConsoleSnatchAppender.getLogs(Level.DEBUG);
        assertEquals(1, lines.size(), "DEBUGログ数");
        assertEquals(prefix + "-DEBUG", lines.get(0), "DEBUGログメッセージ");

        // INFO
        lines = ConsoleSnatchAppender.getLogs(Level.INFO);
        assertEquals(1, lines.size(), "INFOログ数");
        assertEquals(prefix + "-INFO", lines.get(0), "INFOログメッセージ");

        // WARN
        lines = ConsoleSnatchAppender.getLogs(Level.WARN);
        assertEquals(1, lines.size(), "WARNログ数");
        assertEquals(prefix + "-WARN", lines.get(0), "WARNログメッセージ");

        // ERROR
        lines = ConsoleSnatchAppender.getLogs(Level.ERROR);
        assertEquals(1, lines.size(), "ERRORログ数");
        assertEquals(prefix + "-ERROR", lines.get(0), "ERRORログメッセージ");

        // FATAL
        lines = ConsoleSnatchAppender.getLogs(Level.FATAL);
        assertEquals(1, lines.size(), "FATALログ数");
        assertEquals(prefix + "-FATAL", lines.get(0), "FATALログメッセージ");
    }
}
