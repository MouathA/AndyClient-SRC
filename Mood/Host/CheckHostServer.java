package Mood.Host;

import java.util.*;

public class CheckHostServer
{
    private final String name;
    private final String country;
    private final String countryCode;
    private final String city;
    private final List infos;
    
    public CheckHostServer(final String name, final String country, final String countryCode, final String city, final List infos) {
        this.name = name;
        this.country = country;
        this.countryCode = countryCode;
        this.city = city;
        this.infos = infos;
    }
    
    public String getCity() {
        return this.city;
    }
    
    public String getCountry() {
        return this.country;
    }
    
    public String getCountryCode() {
        return this.countryCode;
    }
    
    public String getName() {
        return this.name;
    }
    
    public List getInfos() {
        return this.infos;
    }
}
