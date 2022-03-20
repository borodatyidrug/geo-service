package tests;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.netology.entity.Country;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;

public class LocalizationServiseImplTest {
    
    @Test
    void localeTest() {
        // arrange
        LocalizationService localisationService = new LocalizationServiceImpl();
        Pattern cyrillic = Pattern.compile("[а-яА-ЯЁё\\d\\s\\p{Punct}]*");
        Matcher matcher = cyrillic.matcher(localisationService.locale(Country.RUSSIA));
        
        // act
        boolean resultIsRussianLang = matcher.matches();
        
        // assert
        Assertions.assertTrue(resultIsRussianLang);
    }
}
