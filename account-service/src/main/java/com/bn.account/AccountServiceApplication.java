package com.bn.account;

import com.bn.common.dao.impl.CommonDaoImpl;
import com.bn.common.service.impl.ConfigServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by sbose on 16/5/23.
 */
@SpringBootApplication
@ComponentScan(basePackageClasses = {AccountServiceApplication.class, ConfigServiceImpl.class, CommonDaoImpl.class})
public class AccountServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountServiceApplication.class, args);
	}

}
