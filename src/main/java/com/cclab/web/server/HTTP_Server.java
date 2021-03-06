package com.cclab.web.server;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.cclab.web.server.domain.Member;
import com.cclab.web.server.repository.MemberRepository;
import com.cclab.web.server.domain.Login;
import com.cclab.web.server.repository.LoginRepository;

@SpringBootApplication
public class HTTP_Server {

   @Bean
    InitializingBean saveData(MemberRepository repo) {
        return () -> {
            repo.save(new Member("홍길동", 86, "960330-1306548"));
            repo.save(new Member("오영희", 48, "030702-4138926"));
            repo.save(new Member("김철수", 63, "050810-3306548"));
            repo.save(new Member("윤명숙", 43, "030302-2306548"));
            repo.save(new Member("이하나", 52, "980330-2306548"));
          
        };
    }
    
   @Bean
    InitializingBean saveDataLogin (LoginRepository repo2) {
        return () -> {
            repo2.save(new Login("1002876543","213fdkf","asdf@naver.com",1));
            repo2.save(new Login("1002876542","2dkf","df@naver.com",2));
            repo2.save(new Login("1002876541","21dkf","af@naver.com",3));
            repo2.save(new Login("1002876545","213kf","as@naver.com",4));
            repo2.save(new Login("1002876546","abc","asd@naver.com",5));
          
        };
    }

	public static void main(String[] args) {
		SpringApplication.run(HTTP_Server.class, args);
	}
}
