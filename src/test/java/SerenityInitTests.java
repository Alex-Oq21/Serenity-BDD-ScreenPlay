
import Task.GetUser;
import models.users.Datum;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import org.junit.Test;
import org.junit.runner.RunWith;
import questions.GetUserQuestion;
import questions.ResponseCode;

import static com.google.common.base.Predicates.equalTo;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(SerenityRunner.class)
public class SerenityInitTests {
    private static final String apiUrl = "https://reqres.in/api";

    @Test
    public void getUsers(){
        Actor alex = Actor.named("Alex");
        alex.whoCan(CallAnApi.at(apiUrl));
        alex.attemptsTo(GetUser.frompage(1));
        //assertThat(SerenityRest.lastResponse().statusCode()).isEqualTo(200);

        alex.should(
                seeThat("The status code is ", ResponseCode.was(), equalTo(200))
        );

        Datum user = new GetUserQuestion().answeredBy(alex).getData().stream().filter(x -> x.getId() == 1)
                .findFirst().orElse(null);

        alex.should(
                seeThat("The user exist", act -> user, notNullValue())
        );

        alex.should(
                seeThat("The user Email is", act -> user.getEmail(), equalTo("george.bluth@reqres.in"))
        );
    }
}
