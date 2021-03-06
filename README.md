# Athena

Book Management System based on Spring Framework and Restful API

This is project is inspired by the question from [here](http://www.cnsoftbei.com/bencandy.php?fid=148&aid=1532)
## Install & Preparation
### Add Config file
To install the application, first need to create some config file in resources
* application.properties

    > Setting the Database info

    key | value
    ----|------
    spring.datasource.url | The url of datasource
    spring.datasource.username | Username
    spring.datasource.password | Password
    spring.datasource.driverClassName | Drive class

* config.properties

    > Setting the JWT info

    key | value
    ----|------
    security.token.key|The key of JWT Authentication
    security.token.header | The key of JWT in HTTP header
    security.token.prefix | The prefix of JWT
    security.token.expirationtime | The expiration time of JWT

    > Setting some value regarding the search

    key | value
    ----| -----
    search.default.count| The default value on how much result to return per page

### Install required library
Run following command in command line to install library by maven `mvn`

The following dependencies are required by Athena, you can also find them in the `pom.xml`

* Spring Boot
* Mysql Connector
* Spring Security
* Apache Common
* Jpinyin
* Jjwt

The dependencies below are required for test

* DBUnit
* Spring Security Test
  > Note that the spring security does not contain some of useful component such as @WithMockUser

## Feature
### Overall
The Athena application is a RESTful application, which shall be used with several endpoint such as Android application, Angular/React based website frontend or Wechat App (WeChat Mini Applications)

### Authentication
Currently, Athena has two kind of users, ADMIN and USER.

We define the ADMIN as the admin of library, which has the privilege to lend the books to users.
And the USER shall pay the pledge before they can borrow the book.

### Search
Athena support the following search strategy

* search by name
    * march the partial name (Default)
    * march the exact name
    * march the pronunciation

* search by author
    * march one author in author list
    * march all the author



## Test
This section will introduce the basic test component in Athena
### Structure
The test files is located in `src\test\java\com\athena` it contains following folders

name | content
-----|--------
model| Test the model class and the converter
security | Test the authentication function
service | Test the service class

### Configure
Because the user table has one field name `password`, which is the reserve word of MySQL. So before test something regarding the User table, we need to override some of the default config.
Specifically, We need to config the test as follows:
```java
    @Bean
    public DatabaseConfigBean databaseConfigBean() {
        DatabaseConfigBean databaseConfigBean = new DatabaseConfigBean();
        databaseConfigBean.setEscapePattern("`?`"); //we change the default config here.
        return databaseConfigBean;
    }

    @Bean
    @Autowired
    public DatabaseDataSourceConnectionFactoryBean dbUnitDatabaseConnection(DataSource dataSource, DatabaseConfigBean databaseConfigBean) {
        DatabaseDataSourceConnectionFactoryBean factoryBean = new DatabaseDataSourceConnectionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setDatabaseConfig(databaseConfigBean);
        return factoryBean;
    }
```

### Test Case Explanation
#### model.BooKTest
* Find one book which ISBN is 9787111124444, assert whether the book is the intend one.

#### model.PublisherTest
* Assert whether the publisher can access the book it published

#### model.UserTest
* Create an user, then save it to the database.
* Change some attribute of the User, Assert the password does not be encrypt again.

#### model.WriterConverter
* Assert the WriterConverter can change the String[] into String concatenated by `,`

