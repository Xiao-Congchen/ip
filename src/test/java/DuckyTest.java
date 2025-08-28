import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class DuckyTest {
    @Test
    public void missingFromFieldTest(){
        String expected = "No '/from'? Welp guess I’ll just float around until you tell me :/";
        assertEquals(expected, Ducky.simulator("event no from time /to 14/12/2022 1800"));
    }

    @Test
    public void selectorOutOfRangeTest(){
        String expected = "Hmm, I can’t find that task. Did you drop it in the pond?";
        assertEquals(expected, Ducky.simulator("mark 12345"));
    }
}