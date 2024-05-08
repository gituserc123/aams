package com.aier.cloud;

import org.springframework.boot.SpringApplication;

import com.aier.cloud.center.common.annotion.AierCloudApplication;

import net.sf.jsqlparser.JSQLParserException;


/**
 * @author rain_deng
 */
@AierCloudApplication
public class AamsServiceApp {
    
	public static void main(String[] args) throws JSQLParserException {
		SpringApplication.run(AamsServiceApp.class, args);
	}
}
