package com.smplibst.spring.config.client;

import javax.annotation.PostConstruct;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * SpringCloudConfigクライアントクラス.<br>
 * @since 1.0.0
 */
@SpringBootApplication
public class SpringCloudConfigClientApp {

    /**
     * mainメソッド.<br>
     * @param args コマンドライン引数
     * @since 1.0.0
     */
    public static void main(String[] args) {
        SpringApplication.run(SpringCloudConfigClientApp.class, args);
    }

    /**
     * プロパティクラス.<br>
     * @since 1.0.0
     */
    @Data
    @Component
    @ConfigurationProperties("application-settings")
    public static class Props {

        /**
         * ロガー.<br>
         * @since 1.0.0
         */
        private Logger logger = LoggerFactory.getLogger(getClass());

        /**
         * my-profile.<br>
         * @since 1.0.0
         */
        private String myProfile;

        /**
         * my-name.<br>
         * @since 1.0.0
         */
        private String myName;

        /**
         * my-version.<br>
         * @since 1.0.0
         */
        private String myVersion;

        /**
         * 設定内容確認.<br>
         * @since 1.0.0
         */
        @PostConstruct
        public void init() {
            this.logger.debug("Property ->>> "
                    + "myProfile=" + this.myProfile
                    + ", myName=" + this.myName
                    + ", myVersion=" + this.myVersion);
        }
    }
}
