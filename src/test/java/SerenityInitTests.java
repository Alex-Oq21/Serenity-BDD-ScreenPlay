
import Task.GetUser;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import org.junit.Test;
import org.junit.runner.RunWith;
import questions.ResponseCode;

import static com.google.common.base.Predicates.equalTo;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SerenityRunner.class)
public class SerenityInitTests {
    private static final String apiUrl = "https://reqres.in/api";

    @Test
    public void getUsers(){
        Actor alex = Actor.named("Alex");
        alex.whoCan(CallAnApi.at(apiUrl));
        alex.attemptsTo(GetUser.frompage(2));
        //assertThat(SerenityRest.lastResponse().statusCode()).isEqualTo(200);

        alex.should(
                seeThat("The status code is ", ResponseCode.was(), equalTo(200))

        );
    }
}
