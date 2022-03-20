package tests;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.i18n.LocalizationService;
import ru.netology.sender.MessageSender;
import ru.netology.sender.MessageSenderImpl;

public class MessageSenderImplTest {
    
    @Test
    void send_TestIfIpContainsRussianSubnet() {
        // given
        String ipRussianSubnet = "172.10.42.47";
        
        LocalizationService localizationService = Mockito.mock(LocalizationService.class);
        Mockito.when(localizationService.locale(Country.RUSSIA))
                .thenReturn("Привет! Я - Валентина Андреевич Барановы.");
        
        GeoService geoService = Mockito.mock(GeoService.class);
        Mockito.when(geoService.byIp(ipRussianSubnet))
                .thenReturn(new Location("Moscow", Country.RUSSIA, null, 0));
        
        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);
        Map<String, String> entry = new HashMap<>();
        entry.put(MessageSenderImpl.IP_ADDRESS_HEADER, ipRussianSubnet);
        
        Pattern cyrillic = Pattern.compile("[а-яА-ЯЁё\\d\\s\\p{Punct}]*");
        Matcher matcher = cyrillic.matcher(messageSender.send(entry));
        
        // when
        boolean resultRussianText = matcher.matches();
        
        // then
        Assertions.assertTrue(resultRussianText);
    }
    
    @ParameterizedTest
    @ValueSource(strings = {"96.15.15.15", "95.15.15.16"})
    void send_TestIfIpContainsNotRussianSubnet(String arg) {
        // given
        LocalizationService localizationService = Mockito.mock(LocalizationService.class);
        Mockito.when(localizationService.locale(Country.USA))
                .thenReturn("Hi! I am Valentine Andreevich Baranovy.");
        
        GeoService geoService = Mockito.mock(GeoService.class);
        Mockito.when(geoService.byIp(arg))
                .thenReturn(new Location("New York", Country.USA, null, 0));
        
        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);
        Map<String, String> entry = new HashMap<>();
        entry.put(MessageSenderImpl.IP_ADDRESS_HEADER, arg);
        
        Pattern notCyrillic = Pattern.compile("[^а-яА-ЯЁё]*");
        Matcher matcher = notCyrillic.matcher(messageSender.send(entry));
        
        //when
        boolean resultNotRussianText = matcher.matches();
        
        //then
        Assertions.assertTrue(resultNotRussianText);
    }
}
