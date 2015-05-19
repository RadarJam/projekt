package domain.datasources.modulateing;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import domain.datasources.DataSource;
import domain.datasources.model.MetaData;
import domain.jersey.model.Modification;

public class DateModelator implements ModelatingComand
{
	private DataSource dataSource;
	private int years, months, days;

	public DateModelator(DataSource dataSource, int years, int months, int days)
	{
		this.dataSource = dataSource;
		this.years = years;
		this.months = months;
		this.days = days;
	}

	@Override
	public DataSource execute()
	{
		DataSource output = new DataSource()
		{
			
			@Override
			public TreeMap<LocalDate, Double> getData()
			{
				TreeMap<LocalDate, Double> output = new TreeMap<>();
				
				for (Entry<LocalDate, Double> entry : dataSource.getData().entrySet())
				{
					LocalDate key = entry.getKey();
					
					key = manipulateDate(key);
					
					output.put(key, entry.getValue());
				}
				
				return output;
			}

			@Override
			public MetaData getMetaData()
			{
				MetaData output = dataSource.getMetaData();
				List<Modification> list = output.getModList();
				
				if(list == null)
				{
					list = new ArrayList<Modification>();
				}
				
				Modification modification = new Modification(DateModelator.this.dataSource.getMetaData().getTitle(), DateModelator.this.years, DateModelator.this.months, DateModelator.this.days);
				
				list.add(modification);
				
				output.setModList(list); 
								
				return output;
			}		

			@Override
			public void downLoadDataSource(String fromDate, String toDate) {
				// TODO Auto-generated method stub
				
			}
		};
		
		return output;
	}
	
	private LocalDate manipulateDate(LocalDate date)
	{
		date = date.plusDays(days);
		date = date.plusMonths(months);
		date = date.plusYears(years);
		
		return date;
	}
	
}
