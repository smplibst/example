package com.smplibst.spring.config.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * SpringCloudConfigサーバクラス.<br>
 * @since 1.0.0
 */
@SpringBootApplication
@EnableConfigServer
public class SpringCloudConfigServer {

    /**
     * mainメソッド.<br>
     * @param args コマンドライン引数
     * @since 1.0.0
     */
    public static void main(String[] args) {
        SpringApplication.run(SpringCloudConfigServer.class, args);
    }
}
