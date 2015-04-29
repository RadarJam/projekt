package domain.jersey;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;

import javax.ws.rs.core.Response;

import jdk.nashorn.internal.runtime.regexp.JoniRegExp.Factory;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;

import domain.api.serialization.JsonParser;
import domain.datasources.DataSource;
import domain.datasources.DataSourceFactory;
import domain.datasources.model.MetaData;
import domain.datasources.workers.SunAltitudeAtNoon;
import domain.datasources.workers.TotalFotballGoals;

public class TestSingleDataSource {
	TreeMap<LocalDate, Double> data = new TreeMap<>();
	DataSourceFactory factory;
	DataSourceAPI api;
	Response resp;

	@Before
	public void setup() {
		api = new DataSourceAPI();
		data = new TreeMap<>();
		 factory = mock(DataSourceFactory.class);
	}

	@Test
	public void testDataSingleDataSource() {

		data.put(LocalDate.parse("2001-01-02"), 2.0);
		data.put(LocalDate.parse("2001-01-01"), 1.0);

		DataSource mockSource = mock(DataSource.class);
		when(mockSource.getData()).thenReturn(data);

		when(factory.getDataSource("mockSource")).thenReturn(mockSource);

		api.setFactory(factory);
		String expectedJson = "{\"2001-01-01\":1.0,\"2001-01-02\":2.0}";
		System.out.println(api.getSources("mockSource").getEntity());
		assertEquals(expectedJson, api.getSources("mockSource").getEntity());

		assertEquals(Response.Status.OK.getStatusCode(),
				api.getSources("mockSource").getStatus());
	}

	@Test
	public void testDataSingleDataSourceDontExists() {

		api.setFactory(factory);
		String expectedJson = null;
		resp = api.getSources("iDontExists");
		assertEquals(expectedJson, resp.getEntity());
		assertEquals(Response.Status.BAD_REQUEST.getStatusCode(),
				resp.getStatus());
	}

	DataSource x = new DataSource() {

		@Override
		public String getUnit() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public MetaData getMetaData() {
			// TODO Auto-generated method stub
			MetaData meta = new MetaData();
			meta.setTitle("xSource");
			return meta;
		}

		@Override
		public TreeMap<LocalDate, Double> getData() {
			TreeMap<LocalDate, Double> map = new TreeMap<LocalDate, Double>();
			map.put(LocalDate.parse("2001-01-01"), 1d);
			return map;
		}
	};
	DataSource y = new DataSource() {

		@Override
		public String getUnit() {
			return null;
		}

		@Override
		public MetaData getMetaData() {
			MetaData meta = new MetaData();
			meta.setTitle("ySource");
			return meta;
		}

		@Override
		public TreeMap<LocalDate, Double> getData() {
			TreeMap<LocalDate, Double> map = new TreeMap<LocalDate, Double>();
			map.put(LocalDate.parse("2001-01-01"), 1d);
			return map;
		}
	};

	@Test
	public void testGetMetaData() {
		DataSource sc1 = x;
		DataSource sc2 = y;
		String expectedName1 = "xSource";
		String expectedName2 = "ySource";

		when(factory.getDataSource("sc1")).thenReturn(x);
		when(factory.getDataSource("sc2")).thenReturn(y);

		api.setFactory(factory);

		Response resp = api.getCorrelationData("sc1", "sc2", "DAY");
		String entityContent = (String) resp.getEntity();

		Map<String, Object> jsonMap = new Gson().fromJson(entityContent,
				Map.class);

		Map<String, Object> metaDataX = (Map<String, Object>) jsonMap
				.get("xMeta");

		Map<String, Object> metaDataY = (Map<String, Object>) jsonMap
				.get("yMeta");

		assertEquals(expectedName1, metaDataX.get("title"));
		assertEquals(expectedName2, metaDataY.get("title"));
	}

	@Test
	public void testGetMissingDates() {
		TreeMap<LocalDate, Double> data = new TreeMap<>();

		data.put(LocalDate.parse("2001-01-04"), 4.0);

		data.put(LocalDate.parse("2001-01-02"), 2.0);
		data.put(LocalDate.parse("2001-01-01"), 1.0);

		DataSource mockSource = mock(DataSource.class);
		when(mockSource.getData()).thenReturn(data);
		
		when(factory.getDataSource("mockSource")).thenReturn(mockSource);

		api.setFactory(factory);
		resp = api.getSources("mockSource");
		String expectedResp = "{\"2001-01-01\":1.0,\"2001-01-02\":2.0,\"2001-01-03\":null,\"2001-01-04\":4.0}";
		assertEquals(expectedResp, resp.getEntity());
	}
}
