package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.netology.entity.Country;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;

public class GeoServiceImplTest {
    
    @Test
    void byIp_testCountries() {
        // arrange
        String ipRussia = "172.33.251.12";
        String ipMoscow = GeoServiceImpl.MOSCOW_IP;
        String ipNewYork = GeoServiceImpl.NEW_YORK_IP;
        String ipUSA = "96.18.78.187";
        
        Country expectedRussia = Country.RUSSIA;
        Country expectedUSA = Country.USA;
        
        GeoService geoService = new GeoServiceImpl();
        
        // act
        Country resultRussia = geoService.byIp(ipRussia).getCountry();
        Country resultMoscow = geoService.byIp(ipMoscow).getCountry();
        Country resultUSA = geoService.byIp(ipUSA).getCountry();
        Country resultNewYork = geoService.byIp(ipNewYork).getCountry();
        
        // assertions
        Assertions.assertEquals(expectedRussia, resultRussia);
        Assertions.assertEquals(expectedRussia, resultMoscow);
        Assertions.assertEquals(expectedUSA, resultUSA);
        Assertions.assertEquals(expectedUSA, resultNewYork);
    }
    
    @Test
    void byIp_nullsByLocalhost() {
        // arrange
        GeoService geoService = new GeoServiceImpl();
        String localhost = "127.0.0.1";
        String expectedCity = null;
        Country expectedCountry = null;
        String expectedStreet = null;
        int expectedBuilding = 0;
        
        // act
        String resultCity = geoService.byIp(localhost).getCity();
        Country resultCountry = geoService.byIp(localhost).getCountry();
        String resultStreet = geoService.byIp(localhost).getStreet();
        int resultBuilding = geoService.byIp(localhost).getBuiling();
        
        // assertions
        Assertions.assertNull(resultCity);
        Assertions.assertNull(resultStreet);
        Assertions.assertNull(resultCountry);
        Assertions.assertEquals(expectedBuilding, resultBuilding);
    }
}
