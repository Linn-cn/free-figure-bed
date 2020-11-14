package edu.changda.linn.figurebed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class FreeFigureBedApplication {

    public static void main(String[] args) {
        SpringApplication.run(FreeFigureBedApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(){
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        // 连接超时时间 10s
        factory.setConnectTimeout(10000);
        // 读取超时设置 30s
        factory.setReadTimeout(30000);
        return new RestTemplate(factory);
    }
}
