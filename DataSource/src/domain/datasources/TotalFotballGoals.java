package domain.datasources;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import domain.api.EverysportApi;
import domain.api.models.everysport.Event;

public class TotalFotballGoals implements DataSource
{

	@Override
	public String getName()
	{
		return "Totala mål per dag i Allsvenskan";
	}

	@Override
	public String getUnit()
	{
		return "Mål";
	}

	@Override
	public Map<LocalDate, Double> getData()
	{
		Map<LocalDate, Double> output = new TreeMap<LocalDate, Double>();
		List<Event> events = new EverysportApi().getEvents();
		
		for (Event event : events)
		{
			Double totalScore = new Double(event.getHomeTeamScore() + event.getVisitingTeamScore());
			if(output.get(event.getStartDate()) != null)
			{
				output.put(event.getStartDate(), output.get(event.getStartDate()) + totalScore);
			}
			else
			{
				output.put(event.getStartDate(), totalScore);				
			}
		}
		return output;
	}
	
	public static void main(String [] args)
	{
		TotalFotballGoals goals = new TotalFotballGoals();
			
		for (Entry<LocalDate, Double> entry : goals.getData().entrySet())
		{
			System.out.println(entry.getValue());
		}
	}
}