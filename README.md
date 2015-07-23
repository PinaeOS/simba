#Simba#

Simba is dependency injection framework for Java

- Easy to integrated to Java application
- Dependency Injection by XML
- Aspect-Oriented Programming

## Installation ##

Direct Download (2015/7/23)

The lastest stable is simba-1.2.tar.gz (release notes)

Maven

	<dependency>
	    <groupId>org.pinae</groupId>
	    <artifactId>simba</artifactId>
	    <version>1.1</version>
	</dependency>


## Getting Start ##

demo for Simba:

	public class DemoTestManager {
		public static void main(String arg[]) throws Exception {
			Task task = new Task();
			Job job = new Job() {
				public String getName() {
					return "DelayJob";
				}
		
				public boolean execute() throws JobException {
					System.out.println("Now is : " + Long.toString(System.currentTimeMillis()));
					return true;
				}
			};
		
			task.setName("HelloJob");
			task.setJob(job);
			task.setTrigger(new CronTrigger("0-30/5 * * * * * *"));
			
			TaskContainer container = new TaskContainer();
			container.add(task);
			container.start();
		}
	}
	
## Documentation ##

Full documentation is hosted on [HERE](). 
Sources are available in the `docs/` directory.

## License ##

simba is licensed under the Apache License, Version 2.0 See LICENSE for full license text
