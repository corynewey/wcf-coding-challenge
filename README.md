# wcf-coding-challenge

To complete this challenge, I have chosen to implement a very simple set of web pages that make up a flow-control
wizard. I am using Spring MVC with Thymeleaf as the templating engine.

To run the challenge demo, build it with the "mvn clean package" command. The build creates an executable jar file
so to run the demo, just enter this on the command line:
* java -jar target/cellphoneUsage-0.0.1-SNAPSHOT.jar com.wcf.codechallenge.cellphone.CellphoneApplication

Once the application is running, open a browser and access the home page via this url:
* http://localhost:8080/cell-phone/home

You will be prompted to upload the cellphone data csv file. Click the "Browse..." button and select the csv file
that contains the employee cellphone data. Then click "Upload".

On the next screen, you will be prompted to upload the cellphone usage data csv file. Click the "Browse..." button
and select the csv file that contains the employee cellphone usage data. Then click "Upload".

The final screen will be the generated report that contains a header with general information, and a section for
each employee that details that employee's cellphone usage.


# Notes
* I noticed a typo in the cellphone usage data file header (emplyeeId instead of employeeId). My code is fixing the header
before parsing the csv data.
* The instructions say that the report page should contain usage data in a separate "column for each month." However,
doing it that way creates a report that is hard to read because it will not have a fixed width. Instead, I have broken
the report down into sections, one section for each employee. The header for each employee section contains the employee's
Id, Name, cellphone model and purchase date, and the Total Minutes/Data that the employee used. After the section header,
the employee's cellphone usage is detailed, sorted by date (ascending).
* The UI is ***very*** simple and bare-bones. The styling is simple and I'm using a combination of inline styling and
styles stored inside the header of the template file itself, rather than using an external css file. Also note that 
none of the dates are formatted. If this were a real-world project, I would've of course fleshed out the UI and flow
and added other features.
* All data is stored in an H2 in-memory database. It's probably overkill to use a database for this project, but I had
planned on using a sql query to remove duplicate usage csv entries.
* Speaking of duplicates, the instructions say this: "beware that there may be more than one record for an employee on a 
single date, so it is not a perfect data in a perfect world" (sic). That is why I originally chose to store the data in
an H2 database, as stated above. However, after I analyzed the data a bit more, I realized that there weren't any duplicate 
lines, but rather multiple usage reporting records for certain dates. The choice to store the data in a database was still
helpful in handling this case.
* The instructions say that the report must be printable on the local printer. To achieve that requirement, I added a
"Print Report" button that will print the report.
  
# Testing
I have created only one small test. The test assures that the total/average cellphone usage calculations are correct.
In the real world, I would've of course added a number of other tests: per-user calculations tests, tests of error states, 
and others.
