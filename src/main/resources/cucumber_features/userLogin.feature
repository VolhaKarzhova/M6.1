@loginTest
Feature: LogIn Action
  #The user should be able to login into the mailbox if the login and the password are correct.
  #The user should be shown the error message if the login and/or the password are incorrect.
  #The user should be navigated to mail inbox folder, if the login and password are correct.
  Background:
    Given User navigates to MailRu Home Page

  Scenario Outline: Login with Invalid Credentials
    When    User enters "<Login>" and "<Password>" and click submit button
    Then    "<Alert>" message is displayed

    Examples:
      | Login         | Password   | Alert                   |
      | karzhova      | 1584624Qwe | Неверное имя или пароль |
      | volhakarzhova | wreterytuy | Неверное имя или пароль |
      |               | 1584624Qwe | Введите имя ящика       |
      | volhakarzhova |            | Введите пароль          |
      |               |            | Введите имя ящика       |

  @Successful_login
  Scenario: Successful Login with Valid Credentials
    When    User enters "volhakarzhova@mail.ru" and "1584624Qwe" and click submit button
    Then    Check user is authorized successfully

  Scenario: Successful LogOut
    When    User logOut from the mailbox
    Then    MailRu home page is displayed

