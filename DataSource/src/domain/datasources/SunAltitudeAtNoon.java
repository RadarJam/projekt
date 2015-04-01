package domain.datasources;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import domain.api.WeatherAPI;
import domain.api.models.weatherapi.Time;

public class SunAltitudeAtNoon implements DataSource {

	@Override
	public String getName() {

		return "Solens altitud vid 12";
	}

	@Override
	public String getUnit() {

		return "Grader";
	}

	@Override
	public Map<LocalDate, Double> getData() {

		Map<LocalDate, Double> output = new TreeMap<LocalDate, Double>();
		List<Time> times = new WeatherAPI().getTimes();

		for (Time time : times) {
			output.put(time.getDate(), time.getLocation().getSun().getNoon().getAltitude());
		}
		return output;
	}

}
