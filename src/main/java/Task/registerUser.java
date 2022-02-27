package Task;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Post;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class registerUser implements Task {
    public final Object userinfo;

    public registerUser(Object userinfo) {
        this.userinfo = userinfo;
    }

    public static Performable withIfo(Object userinfo){
        return instrumented(registerUser.class, userinfo);
    }
    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Post.to("/register").with(RequestSpecification ->
                        RequestSpecification.contentType(ContentType.JSON).body(userinfo)
                )
        );
    }
}
