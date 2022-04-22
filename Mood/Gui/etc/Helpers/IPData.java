package Mood.Gui.etc.Helpers;

public class IPData
{
    private final String Organisation;
    private final String Country;
    private final String City;
    private final String Region;
    private final String AS;
    private final String ISP;
    
    public IPData(final String organisation, final String country, final String city, final String region, final String as, final String isp) {
        this.Organisation = organisation;
        this.Country = country;
        this.City = city;
        this.Region = region;
        this.AS = as;
        this.ISP = isp;
    }
}
