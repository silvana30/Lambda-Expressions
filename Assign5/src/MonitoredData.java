import org.joda.time.DateTime;



import java.util.*;
import java.util.function.Function;
import java.util.stream.*;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;


public class MonitoredData {
	public  DateTime startTime;
	public  DateTime endTime;
	public  String activityLabel;

	public List<MonitoredData> monitoredData;

	public MonitoredData(DateTime startTime, DateTime endTime, String activityLabel) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.activityLabel = activityLabel;

	}

    @SuppressWarnings("deprecation")
    public static int nrOfDays(List<MonitoredData> list){
	    //Count the distinct days that appear in the monitoring data
        return (int) list
                .stream()
                .map(m -> m.getStartTime().toDate().getDate())
                .distinct()
                .count();
    }

    public static Map<String, Long> getActiDist(List<MonitoredData> list) {
    /*Determine a map of type <String, Integer> that maps to each distinct action type
the number of occurrences in the log. Write the resulting map into a text file*/
        return list
                .stream()
                .map(m -> m.getActivityLabel())
                .collect(groupingBy(Function.identity(), Collectors.counting()));
    }

    public static Map<Integer,Map<String,Long>> getnrAod(List<MonitoredData> list){
        /*Generates a data structure of type Map<Integer, Map<String, Integer>> that
contains the activity count for each day of the log (task number 2 applied for each
day of the log)and writes the result in a text file*/
        return list
                .stream()
                .collect(groupingBy(m->m.getStartTime().getDayOfMonth(),groupingBy(m->m.getActivityLabel(),counting())));

    }
	
    public static Map<String, Long>  actDuration(List<MonitoredData> list){
        /*Determine a data structure of the form Map<String, DateTime> that maps
for each activity the total duration computed over the monitoring period. Filter the
activities with total duration larger than 10 hours. Write the result in a text file.*/
        Map<String, Long> rez= list
                .stream()
                    .collect(Collectors.groupingBy(p -> p.getActivityLabel(),
                            Collectors.summingLong(
                p -> (p.getEndTime().getMillis() - p.getStartTime().getMillis())
                        / 3600000)))
                    .entrySet().stream().filter(m -> m.getValue() > 10)
                   .collect(Collectors.toMap(m->m.getKey(), m->m.getValue()));
        return rez;

        }

    public static List<String> getFilteredAct(List<MonitoredData> list) {
    /*Filter the activities that have 90% of the monitoring samples with duration
less than 5 minutes, collect the results in a List<String> containing only the
distinct activity names and write the result in a text file.
*/

        Map<String,Long> a=getActiDist(list);
        Map<String, Long> b = list.stream().filter(
                m -> m.getEndTime().getSecondOfDay() - m.getStartTime().getSecondOfDay() < 300)
                .map(m -> m.getActivityLabel())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        return  list.stream()
                .filter(m -> b.get(m.getActivityLabel()) != null
                        && b.get(m.getActivityLabel()) >= 0.9 * a.get(m.getActivityLabel()))
                .map(m -> m.getActivityLabel()).distinct().collect(Collectors.toList());

    }

	public  DateTime getStartTime()
    {
		return startTime;
	}

	public DateTime getEndTime() {

		return endTime;
	}

	String getActivityLabel() {
		return activityLabel;
	}


}
						
						

