package mailRu.cucumber.steps_definitions;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import mailRu.business_objects.letter.Letter;
import mailRu.pages.HeaderMenuPage;
import mailRu.pages.MailListPage;
import mailRu.pages.NewLetterPage;
import mailRu.services.MailService;
import org.testng.Assert;

public class FillLetterInputsSteps {

    private NewLetterPage newLetterPage = new NewLetterPage();
    private MailService mailService = new MailService();

    @When("^user clicks 'New Letter' Button$")
    public void user_clicks_new_letter_button() {
        new HeaderMenuPage().clickNewLetterButton();
    }

    @And("^fill 'Addressee' with \"([^\"]*)\", fill 'Subject' with \"([^\"]*)\", fill 'Body' with \"([^\"]*)\"$")
    public void fill_all_letter_inputs(String addressee, String subject, String body) {
        newLetterPage.fillAllLetterInputs(addressee,subject,body);
    }

    @And("^click 'Save As Draft' button$")
    public void save_letter_as_draft() {
        newLetterPage.saveDraftMail();
    }

    @And("^letter is saved to draft folder$")
    public void check_letter_present_in_draft_folder(Letter letter) {
        Assert.assertTrue(mailService.isLetterVisibleInDraftFolder(letter));
    }
}
