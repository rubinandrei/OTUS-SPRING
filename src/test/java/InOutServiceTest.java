import org.junit.Test;
import questionnaire.services.InOutServices;
import questionnaire.services.InOutServicesImpl;

import java.io.ByteArrayInputStream;
import java.math.BigInteger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class InOutServiceTest {


    @Test
    public void readAnswerTest() {
        System.setIn(new ByteArrayInputStream(String.valueOf(4).getBytes()));
        InOutServices services = new InOutServicesImpl();
        assertThat(services.readAnswer(5), is(4));
    }


}
