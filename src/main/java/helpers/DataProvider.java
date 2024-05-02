package helpers;

import org.junit.jupiter.params.provider.Arguments;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class DataProvider {
    public static Stream<Arguments> setFulLoginParameters(){
        return Stream.of(
                Arguments.of("eve.holt@reqres.in","cityslicker")
        );
    }
    public static Stream<Arguments> setEmailLoginParameters(){
        return Stream.of(
                Arguments.of("peter@klaven")
        );
    }
}
