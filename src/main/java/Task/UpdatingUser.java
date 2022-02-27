package Task;

import io.restassured.http.ContentType;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Put;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class UpdatingUser implements Task {
    private final String infoUser;

    public UpdatingUser(String infoUser) {
        this.infoUser = infoUser;
    }

    public static Performable info(String infoUser){
        return instrumented(UpdatingUser.class, infoUser);
    }
    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Put.to("/users/2").with(
                        requestSpecification
                                -> requestSpecification.contentType(ContentType.JSON).body(infoUser)
                )
        );
    }
}
