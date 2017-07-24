@draftLetterCreation
Feature: Draft Letter Test
  # The user should be able to save draft letter.
  # Draft Letter is saved in the draft folder by default

  Background:
    Given User navigates to MailRu Home Page
    Given Check user is authorized successfully
    Given The JSON for Letter Inputs Filling is
  """
     {
        "array": [
          {
            "Addressee": volhakarzhova@mail.ru,
            "Subject": "Subject464647",
            "Body": "wetyjiolkmn876re3"
          },
          {
            "Addressee": volhakarzhova@mail.ru,
            "Subject": "",
            "Body": ""
          }
        ]
    """

  Scenario  Authorized user is able to save draft letter
    When    User clicks 'New Letter' Button
    And     Fill 'Addressee' with "<Addressee>", fill 'Subject' with "<Subject>", fill 'Body' with "<Body>"
    And     Click 'Save As Draft' button
    Then    Letter is saved to draft folder




