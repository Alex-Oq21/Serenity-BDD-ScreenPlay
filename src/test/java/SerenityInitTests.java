import Task.GetUser;
import Task.UpdatingUser;
import Task.registerUser;
import models.users.Datum;
import models.users.PostUserInfo;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import org.junit.Test;
import org.junit.runner.RunWith;
import questions.GetUserQuestion;
import questions.ResponseCode;

import java.util.Objects;

import static com.google.common.base.Predicates.equalTo;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(SerenityRunner.class)
public class SerenityInitTests {
    private static final String apiUrl = "https://reqres.in/api";

    @Test
    public void getUsers() {
        Actor alex = Actor.named("Alex");
        alex.whoCan(CallAnApi.at(apiUrl));
        alex.attemptsTo(GetUser.frompage(1));


        Datum user = new GetUserQuestion().answeredBy(alex).getData().stream().filter(
                x -> x.getId() ==1).findFirst().orElse(null);

        alex.should(
                seeThat("See the user is not null", act -> user, notNullValue())
        );

        alex.should(
                seeThat("The user Email is", act -> user.getEmail(), equalTo("george.bluth@reqres.in"))
        );

        alex.should(
                seeThat("The Avatar is", act -> user.getAvatar(), equalTo("https://reqres.in/img/faces/1-image.jpg"))
        );
        alex.should(
                seeThat("The status code is ", ResponseCode.was(), equalTo(200))

        );

    }

    @Test
    public void RegisterUserTest(){

        Actor alex = Actor.named("Alex");
        alex.whoCan(CallAnApi.at(apiUrl));

        PostUserInfo postUserInfo = new PostUserInfo();
        postUserInfo.setName("Morpheus");
        postUserInfo.setJob("Leader");
        postUserInfo.setEmail("tracey.ramos@reqres.in");
        postUserInfo.setPassword("serenity");

        alex.attemptsTo(registerUser.withIfo(postUserInfo));

        alex.should(
                seeThat("The status code is", new ResponseCode(), equalTo(200))
        );
    }

    @Test
    public void UpdatingUserTest(){
        Actor alex = Actor.named("Alex");
        alex.whoCan(CallAnApi.at(apiUrl));
        String userUpdateData = "{\n" +
                "    \"name\": \"Morpheus\",\n" +
                "    \"job\": \"Leader\",\n" +
                "    \"email\": \"tracey.ramos@reqres.in\",\n" +
                "    \"password\": \"serenety\"\n" +
                "}";

        alex.attemptsTo(UpdatingUser.info(userUpdateData));

        alex.should(
                seeThat("The status code is", new ResponseCode(), equalTo(200))
        );
    }
}
