package com.smplibst.log4j2.appender.custom;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Core;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.appender.ConsoleAppender.Target;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.util.Assert;

/**
 * 標準出力をハックするアペンダークラス.<br>
 * <p>
 * JUnitなどのテストでログの内容も確認したい場合など便利...？<br>
 * マルチスレッド環境下では正しく動作しません。<br>
 * </p>
 * @since 1.0.0
 */
@Plugin(name = ConsoleSnatchAppender.PLUGIN_NAME, category = Core.CATEGORY_NAME, elementType = Appender.ELEMENT_TYPE, printObject = true)
public class ConsoleSnatchAppender extends AbstractAppender {

    /** プラグイン名. */
    public static final String PLUGIN_NAME = "ConsoleSnatch";

    /** ConsoleAppender. */
    private ConsoleAppender consoleAppender;

    /** ログレベル単位の出力内容を保持するマップ. */
    private static Map<Level, List<String>> logs = new HashMap<>();

    /**
     * コンストラクタ.<br>
     * @param name 名前
     * @param layout レイアウト情報
     * @since 1.0.0
     */
    protected ConsoleSnatchAppender(String name, Layout<? extends Serializable> layout) {
        super(name, null, layout, false, null);

        // 実際に標準出力を行うアペンダーを生成する。
        this.consoleAppender = ConsoleAppender.newBuilder()
            .setName(name)
            .setTarget(Target.SYSTEM_OUT)
            .setFollow(true)
            .setDirect(false)
            .setLayout(layout)
            .setIgnoreExceptions(false)
            .build();
    }

    /**
     * Appender生成メソッド.<br>
     * @param name 名前
     * @param layout レイアウト
     * @return Appender
     * @since 1.0.0
     */
    @PluginFactory
    public static ConsoleSnatchAppender createAppender(
        @PluginAttribute("name") String name,
        @PluginElement("Layout") Layout<? extends Serializable> layout) {
        return new ConsoleSnatchAppender(name, layout);
    }

    /**
     * {@inheritDoc}
     * @see org.apache.logging.log4j.core.Appender#append(org.apache.logging.log4j.core.LogEvent)
     * @since 1.0.0
     */
    @Override
    public void append(LogEvent event) {

        // ログレベルを取得する。
        Level level = event.getLevel();

        // レベル毎のログ一覧を取得する。
        List<String> lines = logs.get(level);
        // 存在しない場合はリストを生成する。
        if (lines == null) {
            lines = new ArrayList<>();
            logs.put(level, lines);
        }
        // ログメッセージを保存する。
        lines.add(event.getMessage().getFormattedMessage());

        // ログ出力のため、ConsoleAppenderのappend処理を呼び出す。
        this.consoleAppender.append(event);
    }

    /**
     * 指定ログレベルのログ一覧を取得する.<br>
     * <p>
     * ALLを指定すると全レベルのログが取得出来る。<br>
     * ただし、時間は順番にならないので要注意!!<br>
     * </p>
     * @param level ログレベル
     * @return ログ一覧
     * @since 1.0.0
     */
    public static List<String> getLogs(Level level) {

        Assert.requireNonEmpty(level, "level is null.");

        // ALL指定時は全ログのリストを作成する。
        if (Level.ALL.equals(level)) {
            List<String> allLogs = new ArrayList<>();
            logs.values().stream().forEach(lines -> allLogs.addAll(lines));
        }
        // 指定レベルのログを返却する。
        return logs.getOrDefault(level, List.of());
    }

    /**
     * ログをクリアする.<br>
     * @since 1.0.0
     */
    public static void clear() {
        logs.clear();
        System.out.println("cleared");
    }
}
