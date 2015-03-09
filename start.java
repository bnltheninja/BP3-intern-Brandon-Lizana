/*
	This program was created by Brandon Lizana
	The "currentNumberOfOpenAndClosedTasks method" is used for the 1st challenge, and takes in a string formated as a date given in the .json file
	The "numberOfTasksOpenedAndClosedTasksBetweenDates" method is used to satisfy the 2nd challenge, and takes in two dates formated as the dates given in the .json file. Tasks that are both opened and closed between these dates will be counted in both counts
	The "mostRecentTask" method is used to satisfy the 3rd challenge, and takes in a string instanceId
	The "numberOfTasks" method is used to satisfy the 4th challenge, and takes in a string instanceId
*/
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.ArrayList;
public class start {

	public static void currentNumberOfOpenAndClosedTasks(String date) throws FileNotFoundException //The method only counts a task once. So if it was both opened and closed before the date, it is only counted as closed
	{
		Scanner test = new Scanner(new File("src/task.json"));
		ArrayList<Task> tasks = new ArrayList<Task>();
		enterTasks(test,tasks);
		System.out.println("The number of open and closed tasks before this date is " + numOpenandClosed(tasks,date));
	}
	
	public static void numberOfTasksOpenedAndClosedTasksBetweenDates(String start, String end) throws FileNotFoundException //A task that is both opened and closed in the range will be counted in both counts
	{
		Scanner test = new Scanner(new File("src/task.json"));
		ArrayList<Task> tasks = new ArrayList<Task>();
		enterTasks(test,tasks);
		int[] numTasks = numInRange(tasks,start,end);
		System.out.println("The number of tasks opened between these dates is " + numTasks[1]);
		System.out.println("The number of tasks closed between these dates is " + numTasks[0]);
	}
	
	public static void mostRecentTask(String instanceId) throws FileNotFoundException //The instance id needs to be given as a string
	{
		Scanner test = new Scanner(new File("src/task.json"));
		ArrayList<Task> tasks = new ArrayList<Task>();
		enterTasks(test,tasks);
		System.out.println("The most recent task with this instanceId is " + recentTask(tasks,instanceId,false));
	}
	
	public static void numberOfTasks(String instanceId) throws FileNotFoundException //The instance id needs to be given as a string
	{
		Scanner test = new Scanner(new File("src/task.json"));
		ArrayList<Task> tasks = new ArrayList<Task>();
		enterTasks(test,tasks);
		System.out.println("The number of tasks with this instanceId is " + recentTask(tasks,instanceId,true));
	}
	
	private static String recentTask(ArrayList<Task> tasks, String instanceId, boolean count) //This method is used to either find the most recent tasks with an instance id or count the number of tasks with this instance id, depending on whether or no the boolean count is true or false
	{
		Task temp = new Task();
		Integer counter = 0;
		for(Task j: tasks)
		{
			if(j.getInstanceId() != null && j.getInstanceId().equals(instanceId))
			{
				if(temp.getName()== null)
					temp = j;
				else if(checkDate(j.getCreateDate(),temp.getCreateDate(),true) == 1)
					temp = j;
				counter++;
			}
		}
		return ((count)?counter.toString():temp.getName());
	}
	
	private static int[] numInRange(ArrayList<Task> tasks, String open, String close) //This method is used to find the number of open and closed tasks within a date range
	{
		int[] nums = new int[2];
		for(Task j: tasks)
		{
			int closed = 1;
			int opened = 0;
			if(j.getStatus().equals("Closed"))
			{
				closed = checkDate(j.getCloseDate(), open,false);
				opened = checkDate(j.getCloseDate(),close,true);
				if(opened != 0 && closed !=0) //Makes sure the close date is in between the two given dates
					nums[0] += 1;
			}
			closed = checkDate(j.getCreateDate(), open,false);
			opened = checkDate(j.getCreateDate(),close,true);
			if(opened != 0 && closed !=0) //Makes sure the open date is in between the two given dates
				nums[1] += 1;
			
			
		}
		return nums;
	}
	
	private static void enterTasks(Scanner test, ArrayList<Task> tasks) throws FileNotFoundException //This method is used to read in the task file and place each task into an arraylist of class type task
	{
		test.nextLine();
		while(test.hasNext())
		{
			String breaker = test.nextLine();
			if(breaker.matches(".*].*")) //if we reach the closed bracket, no more tasks need to be added
				break;
			Task temp = new Task();
			String[] line = test.nextLine().split("\"");
			if(line.length > 3) //Checks if the item being read is null or not
				temp.setInstanceName(line[3]);
			line = test.nextLine().split("\"");
			if(line.length > 3) //Checks if the item being read is null or not
				temp.setDueDate(line[3]);
			line = test.nextLine().split("\"");
			if(line.length > 3) //Checks if the item being read is null or not
				temp.setPriority(line[3]);
			line = test.nextLine().split("\"");
			if(line.length > 3) //Checks if the item being read is null or not
				temp.setCloseDate(line[3]);
			line = test.nextLine().split("\"");
			if(line.length > 3) //Checks if the item being read is null or not
				temp.setInstanceStatus(line[3]);
			line = test.nextLine().split("\"");
			if(line.length > 3) //Checks if the item being read is null or not
				temp.setAssigneeType(line[3]);
			line = test.nextLine().split("\"");
			if(line.length > 3) //Checks if the item being read is null or not
				temp.setCreateDate(line[3]);
			line = test.nextLine().split("\"");
			if(line.length > 3) //Checks if the item being read is null or not
				temp.setName(line[3]);
			line = test.nextLine().split("\"");
			if(line.length > 3) //Checks if the item being read is null or not
				temp.setUrl(line[3]);
			line = test.nextLine().split("\"");
			if(line.length > 3) //Checks if the item being read is null or not
				temp.setAssignee(line[3]);
			line = test.nextLine().split("\"");
			if(!line[2].matches(".*null.*")) //Checks if the item being read is null or not
				temp.setInstanceId(line[2].substring(2,line[2].indexOf(",")));
			line = test.nextLine().split("\"");
			if(line.length > 3) //Checks if the item being read is null or not
				temp.setStatus(line[3]);
			line = test.nextLine().split("\"");
			String var = "";
			if(line.length > 3) //if only one variable, just add it
				var = line[3];
			else if(!line[2].matches(".*}.*")) //In case of multiple variables, keep adding, or if no variables, don't add at all
			{
				line = test.nextLine().split("\"");
				while(line.length > 3) //Used to keep adding to the variables attribute
				{
				var +=line[3] + "\n";
				line = test.nextLine().split("\"");
				}
			}
			temp.setVariables(var);
			line = test.nextLine().split("\"");
			if(line.length > 3) //Checks if the item being read is null or not
				temp.setProcessName(line[3]);
			line = test.nextLine().split("\"");
			if(line.length > 3) //Checks if the item being read is null or not
				temp.setId(line[3]);
			test.nextLine();
			tasks.add(temp);
		}
		test.close();

	}
	
	private static int numOpenandClosed(ArrayList<Task> tasks, String date) // This method is used to find the number of tasks opened and closed before or on a set date
	{
		int numTasks = 0;
		
		for(Task j: tasks)
		{
			if(j.getStatus().equals("Closed") && j.getCloseDate().equals(date)) //If the dates are equal, add it
				numTasks++;
			else if(j.getStatus().equals("Closed"))
				numTasks += checkDate(j.getCloseDate(), date,true);
			else
				numTasks += checkDate(j.getCreateDate(), date,true);
			
		}
		return numTasks;
	}
	
	private static int checkDate(String tempDate, String date, boolean isBefore) //Used to find out if the date of the task is before or after the sent in date, depending on the boolean isBefore
	{
		int dateYear = Integer.parseInt(date.substring(0,4));
		int dateMonth = Integer.parseInt(date.substring(5,7));
		int dateDay = Integer.parseInt(date.substring(8,10));
		int dateHour = Integer.parseInt(date.substring(11,13));
		int dateMinute = Integer.parseInt(date.substring(14,16));
		int dateSecond = Integer.parseInt(date.substring(17,19));
		int tempDateYear = Integer.parseInt(tempDate.substring(0,4));
		int tempDateMonth = Integer.parseInt(tempDate.substring(5,7));
		int tempDateDay = Integer.parseInt(tempDate.substring(8,10));
		int tempDateHour = Integer.parseInt(tempDate.substring(11,13));
		int tempDateMinute = Integer.parseInt(tempDate.substring(14,16));
		int tempDateSecond = Integer.parseInt(tempDate.substring(17,19));
		int x = (isBefore)?0:1; //X and Y decide whether we want to add to the count based on if we are checking before or after a specific date
		int y = (isBefore)?1:0;
		if(tempDateYear > dateYear)
			return x;
		else if(tempDateYear < dateYear)
			return y;
		if(tempDateMonth > dateMonth)
			return x;
		else if(tempDateMonth < dateMonth)
			return y;
		if(tempDateDay > dateDay)
			return x;
		else if(tempDateDay < dateDay)
			return y;
		if(tempDateHour > dateHour)
			return x;
		else if(tempDateHour < dateHour)
			return y;
		if(tempDateMinute > dateMinute)
			return x;
		else if(tempDateMinute < dateMinute)
			return y;
		int z = (tempDateSecond < dateSecond) ? y : x;
		return z;
	}
}
