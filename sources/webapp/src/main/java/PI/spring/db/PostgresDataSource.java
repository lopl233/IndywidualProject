package PI.spring.db;


import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class PostgresDataSource extends DriverManagerDataSource {
    public PostgresDataSource(){
        super();
        setDriverClassName("org.postgresql.Driver");
        setUrl("jdbc:postgresql://ec2-46-51-174-85.eu-west-1.compute.amazonaws.com:5432/d8r4p0ip336t9f?sslmode=require");
        setPassword("4223b1522035e1b373f5578ed5c0d4e669b3fe381cc8d6d009dec26a7a4d2678");
        setUsername("ghygigmekhfnjc");
    }

    }
