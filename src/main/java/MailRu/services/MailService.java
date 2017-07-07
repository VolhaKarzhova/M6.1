package MailRu.services;

import MailRu.business_objects.Letter;
import MailRu.pages.*;
import MailRu.tests.CommonMailTest;

public class MailService extends AbstractPage {

    public void moveLetterToSpam(Letter letter) {
        MailListPage mailListPage = new LeftMenuPage().openInboxFolder();
        mailListPage.clickLetterCheckbox(letter.getSubject()).clickSpamButton(letter.getSubject());
    }

    public void moveLetterFromSpam(Letter letter) {
        MailListPage mailListPage = new LeftMenuPage().openSpamFolder();
        mailListPage.clickLetterCheckbox(letter.getSubject()).clickNoSpamButton(letter.getSubject());
    }

    public void saveDraftLetter(Letter letter) {
        NewLetterPage newLetterPage = new HeaderMenuPage().clickNewLetterButton();
        newLetterPage.fillAllLetterInputs(letter.getAddressee(), letter.getSubject(), letter.getBody()).saveDraftMail();
    }

    public void sendLetter(Letter letter) {
        NewLetterPage newLetterPage = new HeaderMenuPage().clickNewLetterButton();
        newLetterPage.fillAllLetterInputs(letter.getAddressee(), letter.getSubject(), letter.getBody())
                .sendMail();
    }

    public void deleteLetter(Letter letter) {
        new MailListPage().deleteLetter(letter.getSubject());
    }

    public void confirmSendingLetterOnAlert() {
        new NewLetterPage().confirmSendingLetterOnAlert();
    }

    public boolean doesAlertMessageMatchExpectedWhileSendingLetterWithBlankSubject() {
        return new NewLetterPage().getEmptyLetterBodyAlertMessage()
                .equalsIgnoreCase(CommonMailTest.ALERT_EMPTY_BODY_MESSAGE);
    }

    public boolean doesAlertMessageMatchExpectedWhileSendingLetterWithInvalidAddressee() {
        return new NewLetterPage().getInvalidAddresseeAlertMessage()
                .equalsIgnoreCase(CommonMailTest.ALERT_INVALID_ADDRESSEE_MESSAGE);
    }

    public boolean doesAddresseeInSuccessfulSendLetterMessageMatchExpected(Letter letter) {
        String addressee = new MailStatusPage().getAddresseeFromSuccessfulSendLetterMessage();
        return (addressee.equalsIgnoreCase(letter.getAddressee()));
    }

    public boolean isLetterVisibleInInboxFolder(Letter letter) {
        new LeftMenuPage().openInboxFolder();
        return isLetterVisibleInFolder(letter);
    }

    public boolean isLetterVisibleInTrashFolder(Letter letter) {
        new LeftMenuPage().openDeletedFolder();
        return isLetterVisibleInFolder(letter);
    }

    public boolean isLetterVisibleInSpamFolder(Letter letter) {
        new LeftMenuPage().openSpamFolder();
        return isLetterVisibleInFolder(letter);
    }

    public boolean isLetterVisibleInDraftFolder(Letter letter) {
        new LeftMenuPage().openDraftFolder();
        return isLetterVisibleInFolder(letter);
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

    private boolean isLetterVisibleInFolder(Letter letter) {
        return new MailListPage().isLetterVisible(letter.getSubject());
    }

    private Letter getLetterBySubject(Letter letter) {
        return new MailListPage().openLetterBySubject(letter.getSubject()).getLetter();
    }

    private Letter getLetterWithBlankSubject() {
        return new MailListPage().openLetterWithoutSubject().getLetter();
    }
}