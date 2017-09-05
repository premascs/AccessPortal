package com.scs.accessportal.configuration;
import java.util.Properties;

import javax.mail.Session;
import javax.mail.Transport;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
/*import org.springframework.orm.hibernate4.HibernateTransactionManager;*/
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@PropertySource("classpath:application.properties")
@EnableTransactionManagement
public class DatabaseConfig {

  @Value("${db.driver}")
  private String DB_DRIVER;
  
  @Value("${db.password}")
  private String DB_PASSWORD;
  
  @Value("${db.url}")
  private String DB_URL;
  
  @Value("${db.username}")
  private String DB_USERNAME;

  @Value("${hibernate.dialect}")
  private String HIBERNATE_DIALECT;
  
  @Value("${hibernate.show_sql}")
  private String HIBERNATE_SHOW_SQL;
  
  @Value("${hibernate.hbm2ddl.auto}")
  private String HIBERNATE_HBM2DDL_AUTO;

  @Value("${entitymanager.packagesToScan}")
  private String ENTITYMANAGER_PACKAGES_TO_SCAN;
  
  @Value("${spring.mail.host}")
  private String SPRING_MAIL_HOST;
  
  @Value("${spring.mail.username}")
  private String SPRING_MAIL_USERNAME;
  
  @Value("${spring.mail.password}")
  private String SPRING_MAIL_PASSWORD;
  
  @Bean
  public DataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(DB_DRIVER);
    dataSource.setUrl(DB_URL);
    dataSource.setUsername(DB_USERNAME);
    dataSource.setPassword(DB_PASSWORD);
    return dataSource;
  }

  @Bean
  public LocalSessionFactoryBean sessionFactory() {
    LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
    sessionFactoryBean.setDataSource(dataSource());
    sessionFactoryBean.setPackagesToScan(ENTITYMANAGER_PACKAGES_TO_SCAN);
    Properties hibernateProperties = new Properties();
    hibernateProperties.put("hibernate.dialect", HIBERNATE_DIALECT);
    hibernateProperties.put("hibernate.show_sql", HIBERNATE_SHOW_SQL);
    hibernateProperties.put("hibernate.hbm2ddl.auto", HIBERNATE_HBM2DDL_AUTO);
    sessionFactoryBean.setHibernateProperties(hibernateProperties);
    
    return sessionFactoryBean;
  }
  
  
  @Bean
  public JavaMailSender getJavaMailSender() {
      JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
      mailSender.setHost(SPRING_MAIL_HOST);
      mailSender.setPort(465);   
      mailSender.setUsername(SPRING_MAIL_USERNAME);
      mailSender.setPassword(SPRING_MAIL_PASSWORD);
       
      Properties props = mailSender.getJavaMailProperties();
      props.put("mail.transport.protocol", "smtp");
      props.put("mail.smtp.auth", "true");
      props.put("mail.smtp.starttls.enable", "true");
      props.put("mail.debug", "true");
      props.put("mail.smtp.starttls.required", "true");
      props.put("mail.smtp.connectiontimeout", "5000");
      props.put("mail.smtp.timeout", "5000");
      props.put("mail.smtp.writetimeout", "5000");
      props.put("mail.smtp.socketFactory.port", "465");
      props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
      props.put("mail.smtp.socketFactory.fallback", "false");
      
     
      return mailSender;
  }
  
 /* spring.mail.properties.mail.smtp.starttls.enable=true
		  spring.mail.properties.mail.smtp.starttls.required=true
		  spring.mail.properties.mail.smtp.connectiontimeout=5000
		  spring.mail.properties.mail.smtp.timeout=5000
		  spring.mail.properties.mail.smtp.writetimeout=5000

		  spring.mail.properties.mail.smtp.auth = true
		  spring.mail.properties.mail.smtp.socketFactory.port = 465
		  spring.mail.properties.mail.smtp.socketFactory.class = javax.net.ssl.SSLSocketFactory
		  spring.mail.properties.mail.smtp.socketFactory.fallback = false
*/
  
 /* @Bean
	public JavaMailSenderImpl javaMailSenderImpl(){
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);
		//Set gmail email id
		mailSender.setUsername("arvindraivns06@gmail.com");
		//Set gmail email password
		mailSender.setPassword("password");
		Properties prop = mailSender.getJavaMailProperties();
		prop.put("mail.transport.protocol", "smtp");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.debug", "true");
		return mailSender;
	}*/

  /*@Bean
  public HibernateTransactionManager transactionManager() {
    HibernateTransactionManager transactionManager = 
        new HibernateTransactionManager();
    transactionManager.setSessionFactory(sessionFactory().getObject());
    return transactionManager;
  }*/


}
