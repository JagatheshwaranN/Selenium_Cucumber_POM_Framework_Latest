# Selenium_Cucumber_POM_Framework_Latest

Issue
<dependency>
			<groupId>io.cucumber</groupId>
			<artifactId>cucumber-java8</artifactId>
			<version>${cucumber.version}</version>
</dependency>
Cucumber Java8 Combination is not working. The step definitions were updated to the Java8 lambda notation. After update, the gherkin steps were not able to 
identify the step definitions.

Solutions Tried
1. Uninstall existing Cucumber Eclipse Plugin and installed again from Eclipse MarketPlace.
2. Install updated Cucumber Eclipse Plugin rom https://cucumber.github.io/cucumber-eclipse/update-site through Install New Software
3. Multiple times project cleanup and build.

Conclusion
Reverting the code base back to normal java format. With normal java format the, gherkin steps able to identify the step definitions and linking is working fine.
After gone through so many forums, found that there is some known issue with cucumber eclipse plugin. Also, have seen some open bug. Not sure, what is the actual 
root cause.

As of now, going with normal java form step definition.

