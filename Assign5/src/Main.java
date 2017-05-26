

import java.io.FileNotFoundException;

import java.io.IOException;
import java.io.PrintWriter;

import java.nio.file.Files;

import java.nio.file.Paths;
import java.util.ArrayList;

import java.util.List;
import java.util.Map;

import java.util.stream.Stream;


import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class Main {

	public static void main(String[] args) {


		List<MonitoredData> monitoredData = new ArrayList<>();

		PrintWriter writer,writer2,writer3,writer4;

		try {
			String fileName = "D://an 2/semestrul 2/TP/Assign5/src/Activities.txt";

			Stream<String> stream = Files.lines(Paths.get(fileName));

			stream.forEach(s -> {
				String[] arr = s.split("\t\t");
				DateTimeFormatter dtf = DateTimeFormat.forPattern("YYYY-MM-dd' 'HH:mm:ss");

				MonitoredData list = new MonitoredData(dtf.parseDateTime(arr[0]), dtf.parseDateTime(arr[1]), arr[2]);

				monitoredData.add(list);

			});

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



		int nr = MonitoredData.nrOfDays(monitoredData);
		System.out.println(nr);

		Map<String, Long> pct2 = MonitoredData.getActiDist(monitoredData);


		try {
			writer = new PrintWriter("D://an 2/semestrul 2/TP/Assign5/src/res1.txt");
			pct2.forEach((key, value) -> writer.println(key + "\t" + value));
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
        }


		
		 Map<Integer, Map<String, Long>> pct3=MonitoredData.getnrAod(monitoredData);


		try {
			writer2 = new PrintWriter("D://an 2/semestrul 2/TP/Assign5/src/res2.txt");
			pct3.forEach((key, value) -> writer2.println(key + "\t" + value));
			writer2.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        Map<String, Long> pct4=MonitoredData.actDuration(monitoredData);

        try {
            writer3 = new PrintWriter("D://an 2/semestrul 2/TP/Assign5/src/res3.txt");
            pct4.forEach((key, value) -> writer3.println(key + "\t" + value));
            writer3.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        List<String> pct5=MonitoredData.getFilteredAct(monitoredData);
        try {
            writer4 = new PrintWriter("D://an 2/semestrul 2/TP/Assign5/src/res4.txt");
            pct5.forEach(m -> writer4.println(m));
            writer4.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


	}
}
