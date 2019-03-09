Framework contains both FE web and API level components and tests.

Getting Started

Clone the repository

cd ~/[your-workspace]
git clone 

Run it from command line from project direcctory:
mvn clean install - should build and install all the dependencies.
Make sure that chromedriver is executable and located in [path_to_project_workspace]/web-ui-tests/src/main/resources/bin/macosx/chromedriver

All tests:
$ mvn test
Single test:
$ mvn -X -Dtest=AuthenticationIT test


Properties files are located in resources/data folder. There you can set api keys, userName and passwords, urls to test.

I've used PageObject Model - in the pageObjects folder located all Page classes - for example HomePage.class
encapsulates all web elements located on that page.
Tests that are using these Page classes are located in:
 [path_to_project_workspace]/web-ui-tests/src/test/java/com/sausedemo/web/tests
 
 Other components, base classes, unitlites, web driver loaders are located under
 path_to_project_workspace[]/web-ui-tests/src/main/java/com
 
 Web Components: saucedemo.web
 API: themoviedb.api.components
 
 Web tests are located under sausedemo.web.tests
 API tests are located under themoviedb.api.tests


Run it from your favorite IDEA - import it from existing sources.
This project uses maven as software project management tool.

For UI tests - run AddItemsToCartTest.java
For API tests - working tests are located in these files:
AuthenticationIT.java
DiscoverIT.java
ListsIT.java
Movies.java





# web-api-test-framework
