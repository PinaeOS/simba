#simba#

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

XML File:

	<beans xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:noNamespaceSchemaLocation="../bean.xsd">
		<bean name="PersonFactory" class="org.pinae.simba.context.resource.Person"
			factory-bean="org.pinae.simba.context.resource.PersonFactory" 
			factory-method="getPerson"
			create="create" run="run" singleton="true" 
			timeout="3" destroy="destroy">
			
			<property name="name">
				<reflection>Name</reflection>
			</property>
			<property name="age">
				<value>27</value>
			</property>
			<property name="email">
				<value>interhui@21cn.com</value>
			</property>
			<property name="admin">
				<value>true</value>
			</property>
		</bean>
		
		<bean name="Name" class="org.pinae.simba.context.resource.Person.Name">
			<constructor>
				<value>hui</value>
				<value>yugeng</value>
			</constructor>
		</bean>
		

	</beans>

Java Bean:

	public class Person {
		
		private static Logger logger = Logger.getLogger(Person.class);
		
		private Name name;
		private int age;
		private String email;
		private boolean admin;
	
		public Name getName() {
			return name;
		}
		public void setName(Name name) {
			this.name = name;
		}
		public int getAge() {
			return age;
		}
		public void setAge(int age) {
			this.age = age;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public boolean isAdmin() {
			return admin;
		}
		public void setAdmin(boolean admin) {
			this.admin = admin;
		}
		public void create(){
			log.debug("Create Person Bean");
		}
		public void run(){
			this.age ++;
		}
		public void destroy(){
			log.debug("Destory Person Bean");
		}
		
		public class Name {
			private String firstName;
			private String lastName;
			
			public Name(String lastName, String firstName){
				this.lastName = lastName;
				this.firstName = firstName;
			}
			public String getFirstName() {
				return firstName;
			}
			public void setFirstName(String firstName) {
				this.firstName = firstName;
			}
			public String getLastName() {
				return lastName;
			}
			public void setLastName(String lastName) {
				this.lastName = lastName;
			}
		}
	}


Java Program:

	public class DemoTestManager {
	
		private static Logger logger = Logger.getLogger(DemoTestManager.class);
	
		public static void main(String arg[]) throws Exception {
			ResourceContext bean = new FileSystemResourceContext("Person.xml");
			Person person = (Person)bean.getBean("PersonFactory");
			
			logger.debug(person.getName().getLastName() + ":" + person.getEmail());
		}
	}
	
## Documentation ##

Full documentation is hosted on [HERE](). 
Sources are available in the `docs/` directory.

## License ##

simba is licensed under the Apache License, Version 2.0 See LICENSE for full license text
