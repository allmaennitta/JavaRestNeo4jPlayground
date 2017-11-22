package de.allmaennitta.mindware.conceptmap;

import org.neo4j.ogm.session.SessionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;
import org.neo4j.ogm.config.Configuration;

@SpringBootApplication
public class Application {

  public static void main(String[] args) { SpringApplication.run(Application.class, args);}

  @Bean
  public SessionFactory getSessionFactory() {
    return new SessionFactory(configuration(), "de.allmaennitta.mindware.conceptmap");
  }

  @Bean
  public Neo4jTransactionManager transactionManager() throws Exception {
    return new Neo4jTransactionManager(getSessionFactory());
  }

  @Bean
  public org.neo4j.ogm.config.Configuration configuration() {
    Configuration config = new org.neo4j.ogm.config.Configuration();
    config.driverConfiguration()
        .setDriverClassName
            ("org.neo4j.ogm.drivers.http.driver.HttpDriver")
        .setURI("http://ingo:ingo@localhost:7474");
    return config;
  }
}
//    @Bean
//    public NodeController learnerController(){
//        return new NodeController();
//    }
//    @Bean
//    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
//        return args -> {
//            System.out.println("Let's inspect the beans provided by Spring Boot:");
//            String[] beanNames = ctx.getBeanDefinitionNames();
//            Arrays.sort(beanNames);
//            for (String beanName : beanNames) {
//                System.out.println(beanName);
//            }
//
//        };
//    }
