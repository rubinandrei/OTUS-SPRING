# QUIZ for testing students 
*(only output questions)*

## Goals:
Application with Spring IoC 
Result: an application configured with an XML context.

## Description :
In resources store questions  and various answers to them in the form of a CSV files:
1. **questions.csv** - file with questions and
2. **answers.csv**  - file answers with reference on questions.

Questions can be with a choice from several options.
The application should simply output the test questions from a CSV file with possible answer choices.

## Unit tests
path: `src/test/java`

## Build:

command:
`mvn clean install`

## Artifact
1. path: `.m2\repository\org\questionnaire\questionnaire\1.0-SNAPSHOT\questionnaire-1.0-SNAPSHOT.jar`
2. run `java -jar questionnaire-1.0-SNAPSHOT.jar `





