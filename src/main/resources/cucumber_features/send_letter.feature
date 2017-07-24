Feature: Common MailRu test
#comment is here - feature description for search testing

#recurrent actions/preconditions go below - in "Background" section
#this section is optional.
#Uncomment it and delete/comment duplicating step from Scenario description
#Background:
#Given user navigates to github home page

  @smokeTest
  Scenario Outline: authorized user can send email with all fields filled
    Given user navigates to malRu home page
    When  enters user credentials and click submit button
    And   check that login is successful
    And   press button 'Create new letter'
    And   enter <addressee> to the 'Addressee' field
    And   enter <subject> to the 'Subject' field
Examples:
    |             addressee |
    | volhakarzhova@mail.ru |
    | olga1584624@gmail.com |