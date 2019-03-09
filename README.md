# web-api-test-framework

Framework contains both FE web and API level components and tests.

# Getting Started

 - Clone repository
 - cd ~/[your-workspace]
 - git clone git@github.com:dtishkoff/web-api-test-framework.git

Make sure that chromedriver has 'x' - execute permissions and located in 
>[path_to_project_workspace]/web-ui-tests/src/main/resources/bin/macosx/chromedriver

To run it from your favorite IDEA - import it from existing sources.
This project uses maven as software project management tool.

To run tests from command line:
```sh
mvn clean install - should build and install all the dependencies.
```

```sh
$ mvn test - will run all the tests.
$ mvn -X -Dtest=AuthenticationIT test - will run single test
```

Properties files are located in resources/data folder. 
There you can set api keys, userName and passwords, urls to test.

### Project Structure

I've used PageObject Model.  
> pageObjects folder contains Web Page classes - for example HomePage.class
which encapsulates all web elements located on that page.

> Tests that are using these Page classes are located in:
 [path_to_project_workspace]/web-ui-tests/src/test/java/com/sausedemo/web/tests
 
 Other components, base classes, unitlites, WebDriver loaders are located under:
 
 > path_to_project_workspace[]/web-ui-tests/src/main/java/com
 
 Web Components: saucedemo.web
 API Components: themoviedb.api.components
 
 Web tests are located under sausedemo.web.tests
 API tests are located under themoviedb.api.tests


# UI tests: 

AddItemsToCartTest.java

# API Working tests:

AuthenticationIT.java
DiscoverIT.java
ListsIT.java
Movies.java





# web-api-test-framework
