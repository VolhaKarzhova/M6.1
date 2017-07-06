package MailRu.service;

import MailRu.business_objects.Letter;
import MailRu.pages.*;
import MailRu.tests.SendMailWithSpamTest;

public class MailService extends AbstractPage {

    public boolean moveLetterToSpam(Letter letter) {
        new LeftMenuPage().openInboxFolder();
        markLetterCheckbox(letter.getSubject());
        new MailListPage().clickSpamButton(letter.getSubject());
        return checkLetterVisibility(letter);
    }

    public boolean moveLetterFromSpam(Letter letter) {
        new LeftMenuPage().openSpamFolder();
        markLetterCheckbox(letter.getSubject());
        new MailListPage().clickNoSpamButton(letter.getSubject());
        return checkLetterVisibility(letter);
    }

    public boolean checkDraftMailIsSavedInDraftFolder(Letter letter) {
        new HeaderMenuPage().clickNewLetterButton();
        new NewLetterPage().fillAllLetterInputs(letter.getAddressee(), letter.getSubject(), letter.getBody()).saveDraftMail();
        return checkIsLetterPresentInDraftFolder(letter);
    }

    public boolean checkAlertMessageWhileSendingLetterWithBlankSubject(Letter letter) {
        sendLetter(letter);
        String alert = new NewLetterPage().getEmptyLetterBodyAlertMessage();
        return (alert.equalsIgnoreCase(SendMailWithSpamTest.ALERT_EMPTY_BODY_MESSAGE));
    }

    public boolean checkAlertMessageWhileSendingLetterWithInvalidAddressee(Letter letter) {
        sendLetter(letter);
        String alert = new NewLetterPage().getInvalidAddresseeAlertMessage();
        return (alert.equalsIgnoreCase(SendMailWithSpamTest.ALERT_INVALID_ADDRESSEE_MESSAGE));
    }

    public boolean checkAddresseeFromSuccessfulSendLetterMessage(Letter letter) {
        sendLetter(letter);
        String addressee = new MailStatusPage().getAddresseeFromSuccessfulSendLetterMessage();
        return (addressee.equalsIgnoreCase(SendMailWithSpamTest.letterWithAllFieldsFilled.getAddressee()));
    }

    public boolean checkAddresseeAfterSendingLetterWithBlankSubject(Letter letter) {
        String addressee = new NewLetterPage().confirmSendingLetterOnAlert().getAddresseeFromSuccessfulSendLetterMessage();
        return (addressee.equalsIgnoreCase(letter.getAddressee()));
    }

    public boolean checkDeletedLetterInFolder(Letter letter) {
        new MailListPage().deleteLetter(letter.getSubject());
        return checkLetterVisibility(letter);
    }

    public boolean checkIsLetterPresentInInboxFolder(Letter letter) {
        new LeftMenuPage().openInboxFolder();
        return checkLetterVisibility(letter);
    }

    public boolean checkIsLetterPresentInTrashFolder(Letter letter) {
        new LeftMenuPage().openDeletedFolder();
        return checkLetterVisibility(letter);
    }

    public boolean checkIsLetterPresentInSpamFolder(Letter letter) {
        new LeftMenuPage().openSpamFolder();
        return checkLetterVisibility(letter);
    }

    private boolean checkIsLetterPresentInDraftFolder(Letter letter) {
        new LeftMenuPage().openDraftFolder();
        return checkLetterVisibility(letter);
    }

    private void markLetterCheckbox(String subject) {
        new MailListPage().clickLetterCheckbox(subject);
    }

    public Letter getReceivedLetterWithBlankSubject() {
        new LeftMenuPage().openInboxFolder();
        return getLetterWithBlankSubject();
    }

    public Letter getSentLetterWithBlankSubject() {
        new LeftMenuPage().openSentFolder();
        return getLetterWithBlankSubject();
    }

    public Letter getReceivedLetter(Letter letter) {
        new LeftMenuPage().openInboxFolder();
        return getLetterBySubject(letter);
    }

    public Letter getSentLetter(Letter letter) {
        new LeftMenuPage().openSentFolder();
        return getLetterBySubject(letter);
    }

    private void sendLetter(Letter letter) {
        new HeaderMenuPage().clickNewLetterButton();
        new NewLetterPage().fillAllLetterInputs(letter.getAddressee(), letter.getSubject(), letter.getBody())
                .sendMail();
    }

    private boolean checkLetterVisibility(Letter letter) {
        return new MailListPage().isLetterVisible(letter.getSubject());
    }

    private Letter getLetterBySubject(Letter letter) {
        return new MailListPage().openLetterBySubject(letter.getSubject()).getLetter();
    }

    private Letter getLetterWithBlankSubject() {
        return new MailListPage().openLetterWithoutSubject().getLetter();
    }
}