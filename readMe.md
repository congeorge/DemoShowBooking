# Demo Show Seating Application

Simple Java application for the use case of booking a Show . This supports the follow operations

## Run Instruction
Run DemoApplication.java file using any IntelliJ and provide commands to run as defined below. It allows for 2 types of users: Admin and Buyer 
Admin needs to login ( default username and password is admin/admin) and then can setup the show and view the seating availability

Select 1 for Admin
Select 2 for Buyer
Given the in-memory nature of the application, the show setup is lost when the application is restarted. 
So testing to be done logged in as Admin.
If logged in as Buyer, the application will allow to book a show and cancel a booking.Should throw invalid command exception when trying any
admin commands.But given there won't be any setup of show stored... testing with Buyer will only show no availability for any show.

## Command Description

| Command       | Params                                                                                     |
|---------------|--------------------------------------------------------------------------------------------|
| Setup         | "Show Number" "Number of Rows" "Number of seats per row" "Cancellation window in minutes"  |
| View          | "Show Number"                                                                              |
| Availability  | "Show Number"                                                               |
| BookShow      | "Show Number" "Phone#" "Comma separated list of seats"                                     |
| CancelBooking | "BookingId#"  "Phone#"                                                                     |
| Quit          |                                                                                            |


## Notes:
Program to be tested using Admin login only.  
Program does not allow updating of show once setup.  
So if a show is setup with 2 rows and 2 seats per row, it cannot be updated to 3 rows and 3 seats per row.  
Program does not allow booking of seats that are not available .So if a show is setup with 2 rows and 2 seats per row, it cannot be booked with 3 seats.  
Program does not allow booking of seats that are already booked.  
Seats can be booked again if previously booked and then cancelled.  
View Simply shows the setup of the show.  
Availability command shows the available seats for a show.  
Shows can be setup with their own cancellation window time in minutes.  
Program ensures you can only cancel a booking within the cancellation window. So if a show is setup with 5 mins as cancellation windoow.  
Any booking for this show cannot be cancelled after 5 minutes of booking.  
TDD tests are in the test folder with the filenames matching the class they test. Testing includes happy path, edge cases and ensures code coverage  
Program user Command factory for each command and new commands can be added easily.  
Program has a simple in-memory data store for storing the shows and bookings.  
TODO:Program can be extended better with adding database support for storing shows and bookings and corresponding transactions.  
TODO: Can add separate controllers to access the core service API.  



## Sample Input Sequence
Admin Login  

Setup 1 show1 2 5 1  
Setup 2 show2 1 2 1  
View 1  
Availability 1  
BookShow 1 90230943 11,12  



CancelBooking 190230943 90230943





//TODO : Maybe add a screen/hall for show seating structure